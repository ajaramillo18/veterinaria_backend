package com.jama.api.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jama.api.dao.ConsultaDAO;
import com.jama.api.dao.GuarderiaDAO;
import com.jama.api.model.Consulta;
import com.jama.api.model.Guarderia;
import com.jama.api.model.Pet;
@Service
public class GuarderiaServiceImpl implements GuarderiaService {

	@Autowired
	private GuarderiaDAO guarderiaDao;
	
	@Override
	@Transactional
	public Guarderia newGuarderia(Guarderia guarderia) {
		return guarderiaDao.save(guarderia);
	}

	@Override
	@Transactional
	public List<Guarderia> getGuarderias() {//listado general
		List<Guarderia> guarderias =  guarderiaDao.findAllByOrderByFechallegadaDesc();
		List<Guarderia> guarderiasFiltradas=new ArrayList();
		for(Guarderia guarderia : guarderias)
			if("Activa".equals(guarderia.getEstatus()))
				guarderiasFiltradas.add(guarderia);

		return guarderiasFiltradas;
	}
	
	@Override
	@Transactional
	public List<Guarderia> getGuarderiasByPet(int Id_Mascota) {//listado especifico
		List<Guarderia> guarderia =  guarderiaDao.getGuarderiaByMascotaIdOrderByFechallegadaDesc(Id_Mascota);
		return guarderia;	
	}
	
	@Override
	@Transactional
	public Guarderia getGuarderia(int Id_guarderia) {
		Optional<Guarderia> guarderiaOpt = guarderiaDao.findById(Id_guarderia);
		Guarderia guarderia = null;
		if(guarderiaOpt.isPresent())		
			guarderia= guarderiaOpt.get();
		
		return guarderia;
	}

	@Override
	@Transactional
	public Guarderia updateGuarderia(Guarderia guarderia, Guarderia bodyGuarderia) {
		//guarderia.setMascotaId(bodyGuarderia.getMascotaId());
		guarderia.setFechallegada(bodyGuarderia.getFechallegada());
		guarderia.setFechasalida(bodyGuarderia.getFechasalida());
		guarderia.setCubiculo(bodyGuarderia.getCubiculo());
		return guarderiaDao.save(guarderia);
	}


	

}
