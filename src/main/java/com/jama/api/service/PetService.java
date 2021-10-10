package com.jama.api.service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Set;

import com.jama.api.model.Client;
import com.jama.api.model.Pet;


public interface PetService {
	public List<Pet> getPets();
	
	public Pet getPet(int Id_Mascota);
	
	public void deletePet(Pet pet,int Id_Mascota);
	
	public Pet save(Pet pet);
	
	public Pet updatePet(Pet pet,Pet BodyPet);
	
	//public Set<Pet> getPetsByClientId(int Id_Cliente);
	public List<Pet> getPetsByClientId(int Id_Cliente);
	
	public  ByteArrayInputStream petListToExcelFile(List<Pet> list);
//////////////////////////////////////
	
	


}
