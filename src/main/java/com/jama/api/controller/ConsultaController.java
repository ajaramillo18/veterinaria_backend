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

import com.jama.api.dao.ConsultaDAO;
import com.jama.api.exception.StudentNotFoundException;
import com.jama.api.model.Client;
import com.jama.api.model.Consulta;
import com.jama.api.model.Guarderia;
import com.jama.api.model.Pet;
import com.jama.api.service.ClientService;
import com.jama.api.service.ConsultaService;
import com.jama.api.service.PetService;

@CrossOrigin("*")
@RestController 
@RequestMapping("/consulta")
public class ConsultaController {
	
	@Autowired
	private PetService petService;
	
	@Autowired
	private ConsultaDAO consultaDao;
	
	@Autowired
	private ConsultaService consultaService;
	
	
	@GetMapping("/listConsultas")//listado general
	public List<Consulta> listPets(Model model)
	{
		List<Consulta> consultaList = consultaService.getConsultas();
		model.addAttribute("consulta", consultaList);
		if(consultaList.isEmpty()) 
			throw new StudentNotFoundException("No hay consultas registradas");
		return consultaList;
	}
	
	@PostMapping(value = "/newConsulta", consumes = MediaType.APPLICATION_JSON_VALUE)//guardar nueva consulta
	public Consulta newConsulta(@RequestBody Consulta bodyConsulta)
	{
		Consulta consultaSaved=null;
		Pet pet = petService.getPet(bodyConsulta.getMascotaId());
		if (pet == null) 
			throw new StudentNotFoundException("Pet id not found - " + bodyConsulta.getMascotaId());
		else
			consultaSaved = consultaService.newConsulta(bodyConsulta);
		return consultaSaved;
	}

	@GetMapping("/getConsultasByPetId/{Id_Mascota}")//obtiene las consultas de una mascota
	public List<Consulta> getConsultasByPetId(@PathVariable int Id_Mascota)
	{
		List<Consulta> consultaList = null;
		Pet pet = petService.getPet(Id_Mascota);
		if (pet == null)
			throw new StudentNotFoundException("Pet id not found - " + Id_Mascota);
		else if(pet!=null)
			consultaList=consultaService.getConsultasByPetId(Id_Mascota);
		if(consultaList.isEmpty()) 
			throw new StudentNotFoundException("La mascota existe pero no tiene consultas registradas");
		return consultaList;
	}
	
	@PutMapping(value = "/updateConsulta")
	public Consulta updateConsulta(@RequestBody Consulta BodyConsulta) {
		Consulta consultaSaved;
		
		Consulta consulta = consultaService.getConsulta(BodyConsulta.getConsultaId());
		if (consulta == null) 
			throw new StudentNotFoundException("Consulta id not found - " + BodyConsulta.getConsultaId());
		else
			consultaSaved = consultaService.updateConsulta(consulta,BodyConsulta);
		return consultaSaved;		
	}
	
	@GetMapping("/download/consulta-report")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=Guarderias.xlsx");
        ByteArrayInputStream stream = consultaService.consultaListToExcelFile(createConsultaData());
        IOUtils.copy(stream, response.getOutputStream());
        System.out.println("Reporte de excel descargado correctamente..........."); 
    }
	
	private List<Consulta> createConsultaData() {
		List<Consulta> consulta =  consultaDao.findAll();
    	return consulta;
	}
	
	@GetMapping("/downloadCsv")
    public void downloadCsv(HttpServletResponse response) throws IOException {
		try { 
	        response.setContentType("text/csv");
	        response.setHeader("Content-Disposition", "attachment; filename=Consulta.csv");
	        // write to csv file //
	        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
	        String[]  headings = {"consultaId","mascotaId","Enfermedad","Fecha"};;
	        String[] pojoclassPropertyName = {"consultaId","mascotaId","Enfermedad","Fecha"};;
	        csvWriter.writeHeader(headings);
	        List<Consulta> consultaList =createConsultaData();
	        if(null!=consultaList && !consultaList.isEmpty()){
	            for (Consulta consulta : consultaList) {
	                csvWriter.write(consulta, pojoclassPropertyName);
	              }
	            }
	        csvWriter.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
        System.out.println("csv report downloaded successfully...........");
    }
}
