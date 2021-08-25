package com.jama.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jama.api.exception.StudentNotFoundException;
import com.jama.api.model.Client;
import com.jama.api.model.Consulta;
import com.jama.api.model.Pet;
import com.jama.api.service.ClientService;
import com.jama.api.service.ConsultaService;
import com.jama.api.service.PetService;

@RestController 
@RequestMapping("/consulta")
public class ConsultaController {
	
	@Autowired
	private PetService petService;
	
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
}
