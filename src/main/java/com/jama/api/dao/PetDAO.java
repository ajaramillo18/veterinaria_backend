package com.jama.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.jama.api.model.Pet;

@CrossOrigin({"http://localhost:8088","http://f790-187-142-20-80.ngrok.io"})
public interface PetDAO extends JpaRepository<Pet, Integer>{
	List<Pet> getPetsByClienteId(int id_Cliente);
	List<Pet> getAllByEstatus(char estatus);
}
