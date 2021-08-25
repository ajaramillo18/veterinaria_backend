package com.jama.api.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jama.api.dao.ClientDAO;
import com.jama.api.model.Client;
import com.jama.api.model.Pet;

@Service
public class ClientServiceImpl implements ClientService{
	
	@Autowired
	private ClientDAO clientDao;
	
	@Override
	@Transactional
	public List<Client> getClients(){
		List<Client> client =  clientDao.getAllByEstatus('D');
		return client;	
	}
	
	@Override
	@Transactional
	public Client getClient(int Id_Cliente){
		Optional<Client> clientOpt = clientDao.findById(Id_Cliente);
		Client client = null;
		if(clientOpt.isPresent())		
			client= clientOpt.get();
		return client;
	}
	
	@Override
	@Transactional
	public void deleteClient(Client client,int Id_Cliente) {
		client.setEstatus('E');//E= Eliminado
		//clientDao.deleteById(Id_Cliente);
	}

	@Override
	@Transactional
	public Client save(Client client) {
		client.setEstatus('D');//D= Disponible
		return clientDao.save(client);
	}
	
	@Override
	@Transactional
	public Client updateClient(Client client, Client BodyClient) {
		//client.setClienteId(BodyClient.getClienteId());
		client.setNombre(BodyClient.getNombre());
		client.setApellidoPaterno(BodyClient.getApellidoPaterno());
		client.setApellidoMaterno(BodyClient.getApellidoMaterno());
		client.setCorreo(BodyClient.getCorreo());
		client.setTelefono(BodyClient.getTelefono());
		client.setDireccion(BodyClient.getDireccion());
		return clientDao.save(client);
	}
	

	


	
}
