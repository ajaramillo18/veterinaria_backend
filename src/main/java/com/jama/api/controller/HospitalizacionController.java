package com.jama.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jama.api.exception.StudentNotFoundException;
import com.jama.api.model.Consulta;
import com.jama.api.model.Guarderia;
import com.jama.api.model.Hospitalizacion;
import com.jama.api.service.ConsultaService;
import com.jama.api.service.HospitalizacionService;

@CrossOrigin("*")
@RestController 
@RequestMapping("/hospitalizacion")
public class HospitalizacionController {
	
	@Autowired
	private HospitalizacionService hospitalizacionService;
	
	@Autowired
	private ConsultaService consultaService;
	
	@PostMapping(value = "/newHospitalizacion", consumes = MediaType.APPLICATION_JSON_VALUE)//guardar nueva Hospitalizacion
	public Hospitalizacion newHospitalizacion(@RequestBody Hospitalizacion BodyHospitalizacion)
	{
		Hospitalizacion HospitalizacionSaved=null;
		Consulta consulta=consultaService.getConsulta(BodyHospitalizacion.getConsultaId());
		if (consulta == null) 
			throw new StudentNotFoundException("Consulta id not found - " + BodyHospitalizacion.getConsultaId());
		else
			HospitalizacionSaved = hospitalizacionService.newHospitalizacion(BodyHospitalizacion);
		return HospitalizacionSaved;
	}
	
	@PutMapping(value = "/updateHospitalizacion")
	public Hospitalizacion updateHospitalizacion(@RequestBody Hospitalizacion BodyHospitalizacion) {
		Hospitalizacion HospitalizacionSaved;
		Hospitalizacion hospitalizacion = hospitalizacionService.getHospitalizacion(BodyHospitalizacion.getHospitalizacionId());
		if (hospitalizacion == null) 
			throw new StudentNotFoundException("Hospitalizacion id not found - " + BodyHospitalizacion.getHospitalizacionId());
		else 
			HospitalizacionSaved = hospitalizacionService.updateGuarderia(hospitalizacion,BodyHospitalizacion);
		return HospitalizacionSaved;		
	}
	
	@GetMapping("/listHospitalizacion")//listado general
	public List<Hospitalizacion> listHospitalizacion(Model model)
	{
		List<Hospitalizacion> HospitalizacionList = hospitalizacionService.getHospitalizaciones();
		model.addAttribute("guarderia", HospitalizacionList);
		if(HospitalizacionList.isEmpty()) 
			throw new StudentNotFoundException("No hay guarderias registradas ");
		return HospitalizacionList;
	}

}
