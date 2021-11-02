package com.jama.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.jama.api.model.Client;

@CrossOrigin({"http://localhost:8088","http://f790-187-142-20-80.ngrok.io"})
public interface ClientDAO extends JpaRepository<Client, Integer>{
	List<Client> getAllByEstatus(char Estatus);
	List<Client> getAllByApellidoPaternoContaining(String apellidoPaterno);
	// :)
}
