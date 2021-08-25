package com.jama.api.service;

import java.util.List;

import com.jama.api.model.Client;
import com.jama.api.model.Pet;


public interface ClientService {
	public List<Client> getClients();
	
	public Client getClient(int Id_Cliente);
	
	public void deleteClient(Client client,int Id_Mascota);
	
	public Client save(Client client);
	
	public Client updateClient(Client client,Client BodyClient);
	
	
//////////////////////////////////////
	
}
