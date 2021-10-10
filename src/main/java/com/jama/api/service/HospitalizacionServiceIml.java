package com.jama.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jama.api.dao.HospitalizacionDAO;
import com.jama.api.model.Guarderia;
import com.jama.api.model.Hospitalizacion;

@Service
public class HospitalizacionServiceIml implements HospitalizacionService {

	@Autowired
	private HospitalizacionDAO hospitalizacionDao;

	@Override
	@Transactional
	public Hospitalizacion newHospitalizacion(Hospitalizacion hospitalizacion) {
		return hospitalizacionDao.save(hospitalizacion);
	}

	@Override
	public Hospitalizacion getHospitalizacion(int hospitalizacionId) {
		Optional<Hospitalizacion> hospitalizacionOpt = hospitalizacionDao.findById(hospitalizacionId);
		Hospitalizacion hospitalizacion = null;
		if(hospitalizacionOpt.isPresent())		
			hospitalizacion= hospitalizacionOpt.get();
		return hospitalizacion;
	}

	@Override
	public Hospitalizacion updateGuarderia(Hospitalizacion hospitalizacion, Hospitalizacion bodyHospitalizacion) {
		hospitalizacion.setConsultaId(bodyHospitalizacion.getConsultaId());
		hospitalizacion.setFechallegada(bodyHospitalizacion.getFechallegada());
		hospitalizacion.setFechasalida(bodyHospitalizacion.getFechasalida());
		hospitalizacion.setCubiculo(bodyHospitalizacion.getCubiculo());
		return hospitalizacionDao.save(hospitalizacion);
	}

	@Override
	public List<Hospitalizacion> getHospitalizaciones() {
		List<Hospitalizacion> hospitalizaciones =  hospitalizacionDao.findAllByOrderByFechallegadaDesc();
		List<Hospitalizacion> hospitalizacionesFiltradas=new ArrayList();
		for(Hospitalizacion hospitalizacion : hospitalizaciones)
			if("Activa".equals(hospitalizacion.getEstatus()))
				hospitalizacionesFiltradas.add(hospitalizacion);
		return hospitalizacionesFiltradas;
	}

}
