package com.jama.api.service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

	@Override
	public ByteArrayInputStream petListToExcelFile(List<Pet> pet) {
		String[] row_heading = {"mascotaId","nombre","cliente_Id","especie","raza","color","nacimiento","sexo","esterilizado","estatus"};
		try{
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Mascotas");
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
	        for(int i = 0; i < pet.size(); i++) {
	        	Row dataRow = sheet.createRow(i + 1);
	        	dataRow.createCell(0).setCellValue(pet.get(i).getMascotaId());
	        	dataRow.createCell(1).setCellValue(pet.get(i).getNombre());
	        	dataRow.createCell(2).setCellValue(pet.get(i).getClienteId());
	        	dataRow.createCell(3).setCellValue(pet.get(i).getEspecie());
	        	dataRow.createCell(4).setCellValue(pet.get(i).getRaza());
	        	dataRow.createCell(5).setCellValue(pet.get(i).getColor());
	        	dataRow.createCell(6).setCellValue(pet.get(i).getNacimiento());
	        	dataRow.createCell(7).setCellValue(pet.get(i).getSexo());
	        	dataRow.createCell(8).setCellValue(pet.get(i).getEsterilizado());
	        	dataRow.createCell(9).setCellValue(pet.get(i).getEstatus());
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
	        sheet.autoSizeColumn(8);
	        sheet.autoSizeColumn(9);
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);
	        return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
}
