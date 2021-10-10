package com.jama.api.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jama.api.dao.ConsultaDAO;
import com.jama.api.dao.GuarderiaDAO;
import com.jama.api.model.Consulta;
import com.jama.api.model.Guarderia;
import com.jama.api.model.Pet;
@Service
public class GuarderiaServiceImpl implements GuarderiaService {

	@Autowired
	private GuarderiaDAO guarderiaDao;
	
	@Override
	@Transactional
	public Guarderia newGuarderia(Guarderia guarderia) {
		return guarderiaDao.save(guarderia);
	}

	@Override
	@Transactional
	public List<Guarderia> getGuarderias() {//listado general
		List<Guarderia> guarderias =  guarderiaDao.findAllByOrderByFechallegadaDesc();
		List<Guarderia> guarderiasFiltradas=new ArrayList();
		for(Guarderia guarderia : guarderias)
			if("Activa".equals(guarderia.getEstatus()))
				guarderiasFiltradas.add(guarderia);
		return guarderiasFiltradas;
	}
	
	@Override
	@Transactional
	public List<Guarderia> getGuarderiasByPet(int Id_Mascota) {//listado especifico
		List<Guarderia> guarderia =  guarderiaDao.getGuarderiaByMascotaIdOrderByFechallegadaDesc(Id_Mascota);
		return guarderia;	
	}
	
	@Override
	@Transactional
	public Guarderia getGuarderia(int Id_guarderia) {
		Optional<Guarderia> guarderiaOpt = guarderiaDao.findById(Id_guarderia);
		Guarderia guarderia = null;
		if(guarderiaOpt.isPresent())		
			guarderia= guarderiaOpt.get();
		return guarderia;
	}

	@Override
	@Transactional
	public Guarderia updateGuarderia(Guarderia guarderia, Guarderia bodyGuarderia) {
		guarderia.setFechallegada(bodyGuarderia.getFechallegada());
		guarderia.setFechasalida(bodyGuarderia.getFechasalida());
		guarderia.setCubiculo(bodyGuarderia.getCubiculo());
		return guarderiaDao.save(guarderia);
	}

	@Override
	public ByteArrayInputStream guarderiaListToExcelFile(List<Guarderia> guarderia) {
		String[] row_heading = {"guarderiaId","mascotaId","fecha_llegada","fecha_salida","cubiculo"};
		try{
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Guarderia");
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
	        for(int i = 0; i < guarderia.size(); i++) {
	        	Row dataRow = sheet.createRow(i + 1);
	        	dataRow.createCell(0).setCellValue(guarderia.get(i).getGuarderiaId());
	        	dataRow.createCell(1).setCellValue(guarderia.get(i).getMascotaId());
	        	dataRow.createCell(2).setCellValue(guarderia.get(i).getFechallegada());
	        	dataRow.createCell(3).setCellValue(guarderia.get(i).getFechasalida());
	        	dataRow.createCell(4).setCellValue(guarderia.get(i).getCubiculo());

	        }
	        // Making size of column auto resize to fit with data
	        sheet.autoSizeColumn(0);
	        sheet.autoSizeColumn(1);
	        sheet.autoSizeColumn(2);
	        sheet.autoSizeColumn(3);
	        sheet.autoSizeColumn(4);
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);
	        return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}


	

}
