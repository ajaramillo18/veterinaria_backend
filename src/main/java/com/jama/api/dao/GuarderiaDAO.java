package com.jama.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.jama.api.model.Guarderia;
@CrossOrigin({"http://localhost:8088","http://f790-187-142-20-80.ngrok.io"})
public interface GuarderiaDAO extends JpaRepository<Guarderia, Integer>{

	List<Guarderia> getGuarderiaByMascotaIdOrderByFechallegadaDesc(int id_Mascota);
	
	List<Guarderia> findAllByOrderByFechallegadaDesc();

}
