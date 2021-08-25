package com.jama.api.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jama.api.exception.StudentNotFoundException;
import com.jama.api.service.ClientService;
import com.jama.api.service.PetService;
import com.jama.api.model.Client;
//
import com.jama.api.model.Pet;



//@Controller
@RestController 
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private PetService petService;
	
	@PostMapping(value = "/saveClient", consumes = MediaType.APPLICATION_JSON_VALUE)//guardar nuevo cliente
	public Client savePet(@RequestBody Client client)
	{
		Client clientSaved = clientService.save(client);
		return clientSaved;
	}

	@PutMapping("/updateClient")//modificar cliente
	public Client updatePet(@RequestBody Client BodyClient) {
		Client clientSaved;
		Client client = clientService.getClient(BodyClient.getClienteId());
		if (client == null)
			throw new StudentNotFoundException("Client id not found - " + BodyClient.getClienteId());
		else
			clientSaved = clientService.updateClient(client,BodyClient);
		return clientSaved;		
		
	}

	@DeleteMapping("/deleteClient/{clienteId}")//eliminar cliente
	public String deleteClient(@PathVariable int clienteId) {
		Client client = clientService.getClient(clienteId);
		if (client == null)
			throw new StudentNotFoundException("Client id not found - " + clienteId);
		else if(client!=null)
			clientService.deleteClient(client,clienteId);
		return "Cliente con id: " + clienteId + " ha sido borrado.";
	}
	
	@GetMapping("/listClient/{clienteId}")//listado especifico
	 public Client getClient(@PathVariable int clienteId){
		 //client=null;
		 Client client = clientService.getClient(clienteId);
		 if (client == null)
				throw new StudentNotFoundException("Client id not found - " + clienteId);
		 return client;
	 }
	
	@GetMapping("/listClients")//listado general
	public List<Client> listClients(Model model)
	{
		List<Client> clientList = clientService.getClients();					
		model.addAttribute("cliente", clientList);
		if(clientList.isEmpty()) 
			throw new StudentNotFoundException("No hay clientes registrados.");
		return clientList;
	}

	@GetMapping("/getPetsByClientId/{clienteId}")//obtiene las mascotas de un cliente
	public List<Pet> getPetsByClientId(@PathVariable int clienteId)
	{
		List<Pet> petsList = null;
		Client client = clientService.getClient(clienteId);
		if (client == null) 
			throw new StudentNotFoundException("Client id not found - " + clienteId);
		else
			petsList=petService.getPetsByClientId(clienteId);
		if(petsList.isEmpty()) 
			throw new StudentNotFoundException("El cliente no tiene ninguna mascota registrada");
		return petsList;
	}
}
