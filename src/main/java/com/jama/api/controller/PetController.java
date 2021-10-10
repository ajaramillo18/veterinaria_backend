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
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.jama.api.dao.ClientDAO;
import com.jama.api.dao.PetDAO;
import com.jama.api.exception.StudentNotFoundException;
import com.jama.api.model.Client;
import com.jama.api.model.Pet;
import com.jama.api.service.PetService;
import com.jama.api.service.PetServiceImpl;
//

@CrossOrigin("*")
@RestController
@RequestMapping("/pets")
public class PetController {
	
	@Autowired
	private PetService petService;
	
	@Autowired
	private PetDAO petDao;
	

	
	
	@PostMapping(value = "/savePet", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Pet savePet(@RequestBody Pet BodyPet)
	{
		Pet petSaved = petService.save(BodyPet);
		return petSaved;
	}
	
	@PutMapping(value = "/updatePet")
	public Pet updatePet(@RequestBody Pet BodyPet) {
		Pet petSaved;
		Pet pet = petService.getPet(BodyPet.getMascotaId());
		if (pet == null) 
			throw new StudentNotFoundException("Pet id not found - " + BodyPet.getMascotaId());
		else 
			petSaved = petService.updatePet(pet,BodyPet);
		return petSaved;		
		
	}
	
	@DeleteMapping("/deletePet/{Id_Mascota}")	
	public String deletePet(@PathVariable int Id_Mascota) {
		
		Pet pet = petService.getPet(Id_Mascota);
		if (pet == null)
			throw new StudentNotFoundException("Pet id not found - " + Id_Mascota);
		else if(pet!=null) 
			petService.deletePet(pet,Id_Mascota);	
		return "Mascota con id: " + Id_Mascota + " ha sido borrado.";
	}
	
	@GetMapping("/listPet/{Id_Mascota}")//listado especifico
	 public Pet getPet(@PathVariable int Id_Mascota){
		 Pet pet = petService.getPet(Id_Mascota);
		 if (pet == null)
				throw new StudentNotFoundException("Pet id not found - " + Id_Mascota);
		 return pet;
		 
	 }

	@GetMapping("/listPets")//listado general
	public List<Pet> listPets(Model model)
	{
		List<Pet> petsList = petService.getPets();
		model.addAttribute("mascota", petsList);
		if(petsList.isEmpty()) 
			throw new StudentNotFoundException("No hay mascotas registradas");
		return petsList;
	}
	
	@GetMapping("/download/pet-report")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=Mascotas.xlsx");
        ByteArrayInputStream stream = petService.petListToExcelFile(createPetData());
        IOUtils.copy(stream, response.getOutputStream());
        System.out.println("Reporte de excel descargado correctamente..........."); 
    }
	
	private List<Pet> createPetData() {
		List<Pet> pets =  petDao.findAll();
    	return pets;
	}
	
	@GetMapping("/downloadCsv")
    public void downloadCsv(HttpServletResponse response) throws IOException {
		try { 
	        response.setContentType("text/csv");
	        response.setHeader("Content-Disposition", "attachment; filename=Mascotas.csv");
	        // write to csv file //
	        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
	        String[]  headings = {"mascotaId","nombre","cliente_Id","especie","raza","color","nacimiento","sexo","esterilizado","estatus"};;
	        String[] pojoclassPropertyName = {"mascotaId","nombre","clienteId","especie","raza","color","nacimiento","sexo","esterilizado","estatus"};;
	        csvWriter.writeHeader(headings);
	        List<Pet> PetList =createPetData();
	        if(null!=PetList && !PetList.isEmpty()){
	            for (Pet pet : PetList) {
	                csvWriter.write(pet, pojoclassPropertyName);
	              }
	            }
	        csvWriter.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
        System.out.println("csv report downloaded successfully...........");
    }
}
