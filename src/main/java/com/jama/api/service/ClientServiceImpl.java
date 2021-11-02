package com.jama.api.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
	
	@Override
	public ByteArrayInputStream clientListToExcelFile(List<Client> client) {
		String[] row_heading = {"cliente_Id","Nombre","Apellido_Paterno","Apellido_Materno","Correo","Telefono","Direccion","estatus"};
		try{
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Clientes");
			Row headerRow = sheet.createRow(0);
	        CellStyle headerCellStyle = workbook.createCellStyle();
	        headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		    Font font = workbook.createFont();
	        font.setColor(IndexedColors.RED.getIndex());
	        headerCellStyle.setFont(font);
	        // Creating header
	        for (int i = 0; i < row_heading.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(row_heading[i]);
				cell.setCellStyle(headerCellStyle);
			}
	        // Creating data rows for each user
	        for(int i = 0; i < client.size(); i++) {
	        	Row dataRow = sheet.createRow(i + 1);
	        	dataRow.createCell(0).setCellValue(client.get(i).getClienteId());
	        	dataRow.createCell(1).setCellValue(client.get(i).getNombre());
	        	dataRow.createCell(2).setCellValue(client.get(i).getApellidoPaterno());
	        	dataRow.createCell(3).setCellValue(client.get(i).getApellidoMaterno());
	        	dataRow.createCell(4).setCellValue(client.get(i).getCorreo());
	        	dataRow.createCell(5).setCellValue(client.get(i).getTelefono());
	        	dataRow.createCell(6).setCellValue(client.get(i).getDireccion());
	        	dataRow.createCell(7).setCellValue(client.get(i).getEstatus());
	        }
	        // Making size of column auto resize to fit with data
	        sheet.autoSizeColumn(0);
	        sheet.autoSizeColumn(1);
	        sheet.autoSizeColumn(2);
	        sheet.autoSizeColumn(3);
	        sheet.autoSizeColumn(4);
	        sheet.autoSizeColumn(5);
	        sheet.autoSizeColumn(6);
	        sheet.autoSizeColumn(7);
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);
	        return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Client> searchClientsByLastName(String lastName) {
		List<Client> client =  clientDao.getAllByApellidoPaternoContaining(lastName);
		return client;	
	}


	
}
