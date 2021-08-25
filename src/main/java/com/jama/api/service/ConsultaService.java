package com.jama.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jama.api.model.Consulta;
import com.jama.api.model.Pet;

public interface ConsultaService {
	public Consulta newConsulta(Consulta consulta);
	
	public List<Consulta> getConsultasByPetId(int Id_Mascota);

	public Consulta getConsulta(int Id_Mascota);
	
	public Consulta updateConsulta(Consulta consulta,Consulta BodyConsulta);

	public List<Consulta> getConsultas();
	
	
//////////////////////////////////////
}
