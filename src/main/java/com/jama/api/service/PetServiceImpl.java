package com.jama.api.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jama.api.dao.ClientDAO;
import com.jama.api.dao.PetDAO;
import com.jama.api.model.Client;
import com.jama.api.model.Guarderia;
import com.jama.api.model.Pet;


@Service
public class PetServiceImpl implements PetService{
	
	@Autowired
	private PetDAO petDao;
	
	@Autowired
	private ClientDAO clientDao;
	
	@Autowired
	private ClientService clientService;
	
	@Override
	@Transactional	
	public List<Pet> getPets(){
		List<Pet> pets =  petDao.getAllByEstatus('D');
		pets=setClientsName(pets);
		
		return pets;	
	}
		
	@Override
	@Transactional
	public Pet getPet(int Id_Mascota){
		Optional<Pet> petOpt = petDao.findById(Id_Mascota);
		Pet pet = null;
		if(petOpt.isPresent()) {
			pet= petOpt.get();
			Client client = clientService.getClient(pet.getClienteId());
			pet.setClientName(client.getNombre() + " " + client.getApellidoPaterno() + " " +client.getApellidoMaterno());
		}
		return pet;
	}
	
	@Override
	@Transactional
	public Pet updatePet(Pet pet,Pet BodyPet) {
		pet.setNombre(BodyPet.getNombre());
		pet.setEspecie(BodyPet.getEspecie());
		pet.setRaza(BodyPet.getRaza());
		pet.setColor(BodyPet.getColor());
		pet.setNacimiento(BodyPet.getNacimiento());
		pet.setSexo(BodyPet.getSexo());
		pet.setEsterilizado(BodyPet.getEsterilizado());
		pet.setClienteId(BodyPet.getClienteId());
		return petDao.save(pet);
	}
	
	@Override
	@Transactional
	public void deletePet(Pet pet,int Id_Mascota) {
		pet.setEstatus('E');//E= Eliminado
		//petDao.deleteById(Id_Mascota);
	}

	@Override
	@Transactional
	public Pet save(Pet pet) {
		pet.setEstatus('D');//D= Disponible
		return petDao.save(pet);

	}

	@Override
	@Transactional
	public List<Pet> getPetsByClientId(int Id_Cliente) {
		//Client client = clientService.getClient(Id_Cliente);
		//return client.getListaPets();	
		List<Pet> pets=petDao.getPetsByClienteId(Id_Cliente);
		pets=setClientsName(pets);
		return pets;
	}

	public List<Pet> setClientsName(List<Pet> pets){
		List<Client> clients =  clientDao.findAll();
		for(Pet pet: pets)
			for(Client client: clients)
				if(client.getClienteId()==pet.getClienteId()) 
					pet.setClientName(client.getNombre() + " " + client.getApellidoPaterno() + " " +client.getApellidoMaterno());
		return pets;
		
	}
	
}
