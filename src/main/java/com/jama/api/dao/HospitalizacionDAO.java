package com.jama.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.jama.api.model.Hospitalizacion;

@CrossOrigin({"http://localhost:8088","http://f790-187-142-20-80.ngrok.io"})
public interface HospitalizacionDAO extends JpaRepository<Hospitalizacion, Integer>{
	List<Hospitalizacion> findAllByOrderByFechallegadaDesc();

}
