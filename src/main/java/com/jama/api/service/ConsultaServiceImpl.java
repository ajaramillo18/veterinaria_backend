package com.jama.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jama.api.dao.ConsultaDAO;
import com.jama.api.model.Consulta;
import com.jama.api.model.Pet;

@Service
public class ConsultaServiceImpl implements ConsultaService {

	@Autowired
	private ConsultaDAO consultaDao;
	
	@Override
	@Transactional
	public Consulta newConsulta(Consulta consulta) {
		return consultaDao.save(consulta);
	}

	@Override
	@Transactional
	public List<Consulta> getConsultasByPetId(int Id_Mascota) {
		List<Consulta> consulta =  consultaDao.getConsultasByMascotaId(Id_Mascota);
		return consulta;	
	}

	@Override
	@Transactional
	public Consulta getConsulta(int Id_Mascota) {
		Optional<Consulta> consultaOpt = consultaDao.findById(Id_Mascota);
		Consulta consulta = null;
		if(consultaOpt.isPresent())		
			consulta= consultaOpt.get();
		return consulta;
	}

	@Override
	@Transactional
	public Consulta updateConsulta(Consulta consulta, Consulta BodyConsulta) {
		//consulta.setMascotaId(BodyConsulta.getMascotaId());
		consulta.setFecha(BodyConsulta.getFecha());
		consulta.setEnfermedad(BodyConsulta.getEnfermedad());
		return consultaDao.save(consulta);
	}

	@Override
	public List<Consulta> getConsultas() {
		List<Consulta> consulta =  consultaDao.findAll();
		return consulta;	
	}

}
