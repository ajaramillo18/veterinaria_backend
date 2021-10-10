package com.jama.api.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.jama.api.dao.GuarderiaDAO;
import com.jama.api.exception.StudentNotFoundException;
import com.jama.api.model.Guarderia;
import com.jama.api.model.Pet;
import com.jama.api.service.GuarderiaService;
import com.jama.api.service.GuarderiaServiceImpl;
import com.jama.api.service.PetService;

@CrossOrigin("*")
@RestController 
@RequestMapping("/guarderia")
public class GuarderiaController {

	
	@Autowired
	private PetService petService;
	
	@Autowired
	private GuarderiaDAO guarderiaDao;
	
	@Autowired
	private GuarderiaService guarderiaService;
	
	@Autowired
	GuarderiaServiceImpl guarderiaServiceImpl;
	
	@PostMapping(value = "/newGuarderia", consumes = MediaType.APPLICATION_JSON_VALUE)//guardar nueva guarderia
	public Guarderia newGuarderia(@RequestBody Guarderia guarderiaConsulta)
	{
		Guarderia guarderiaSaved=null;
		Pet pet = petService.getPet(guarderiaConsulta.getMascotaId());
		if (pet == null) 
			throw new StudentNotFoundException("Pet id not found - " + guarderiaConsulta.getMascotaId());
		else
			guarderiaSaved = guarderiaService.newGuarderia(guarderiaConsulta);
		return guarderiaSaved;
	}
	
	@PutMapping(value = "/updateGuarderia")
	public Guarderia updateGuarderia(@RequestBody Guarderia BodyGuarderia) {
		Guarderia guarderiaSaved;
		Guarderia guarderia = guarderiaService.getGuarderia(BodyGuarderia.getGuarderiaId());
		if (guarderia == null) 
			throw new StudentNotFoundException("Guarderia id not found - " + BodyGuarderia.getGuarderiaId());
		else 
			guarderiaSaved = guarderiaService.updateGuarderia(guarderia,BodyGuarderia);
		return guarderiaSaved;		
	}
	
	@GetMapping("/listGuarderias")//listado general
	public List<Guarderia> listGuarderia(Model model)
	{
		List<Guarderia> guarderiaList = guarderiaService.getGuarderias();
		model.addAttribute("guarderia", guarderiaList);
		if(guarderiaList.isEmpty()) 
			throw new StudentNotFoundException("No hay guarderias registradas ");
		return guarderiaList;
	}
	
	@GetMapping("/getGuarderiasByPet/{Id_mascota}")//obtiene guarderias de la mascota del id ingresado
	public List<Guarderia> getGuarderiasByPet(@PathVariable int Id_mascota)
	{
		List<Guarderia> guarderiaList = null;
		Pet pet = petService.getPet(Id_mascota);
		if (pet == null) 
			throw new StudentNotFoundException("Pet id not found - " + Id_mascota);
		else if(pet!=null)
			guarderiaList=guarderiaService.getGuarderiasByPet(Id_mascota);
		if(guarderiaList.isEmpty()) 
			throw new StudentNotFoundException("Pet id exist but does not have any guarderia- " + Id_mascota);
		return guarderiaList;
	}
	
	@GetMapping("/download/guarderia-report")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=Guarderias.xlsx");
        ByteArrayInputStream stream = guarderiaService.guarderiaListToExcelFile(createGuarderiaData());
        IOUtils.copy(stream, response.getOutputStream());
        System.out.println("Reporte de excel descargado correctamente..........."); 
    }
	
	private List<Guarderia> createGuarderiaData() {
		List<Guarderia> guarderia =  guarderiaDao.findAll();
    	return guarderia;
	}
	
	@GetMapping("/downloadCsv")
    public void downloadCsv(HttpServletResponse response) throws IOException {
		try { 
	        response.setContentType("text/csv");
	        response.setHeader("Content-Disposition", "attachment; filename=Guarderia.csv");
	        // write to csv file //
	        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
	        String[]  headings = {"guarderiaId","mascotaId","fecha_llegada","fecha_salida","cubiculo"};;
	        String[] pojoclassPropertyName = {"guarderiaId","mascotaId","fechallegada","fechasalida","cubiculo"};;
	        csvWriter.writeHeader(headings);
	        List<Guarderia> guarderiaList =createGuarderiaData();
	        if(null!=guarderiaList && !guarderiaList.isEmpty()){
	            for (Guarderia guarderia : guarderiaList) {
	                csvWriter.write(guarderia, pojoclassPropertyName);
	              }
	            }
	        csvWriter.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
        System.out.println("csv report downloaded successfully...........");
    }
}
