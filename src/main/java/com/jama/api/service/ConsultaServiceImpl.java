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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jama.api.dao.ConsultaDAO;
import com.jama.api.model.Consulta;
import com.jama.api.model.Pet;

@Service
public class ConsultaServiceImpl implements ConsultaService {

	@Autowired
	private ConsultaDAO consultaDao;
	
	@Override
	@Transactional
	public Consulta newConsulta(Consulta consulta) {
		return consultaDao.save(consulta);
	}

	@Override
	@Transactional
	public List<Consulta> getConsultasByPetId(int Id_Mascota) {
		List<Consulta> consulta =  consultaDao.getConsultasByMascotaId(Id_Mascota);
		return consulta;	
	}

	@Override
	@Transactional
	public Consulta getConsulta(int Id_Mascota) {
		Optional<Consulta> consultaOpt = consultaDao.findById(Id_Mascota);
		Consulta consulta = null;
		if(consultaOpt.isPresent())		
			consulta= consultaOpt.get();
		return consulta;
	}

	@Override
	@Transactional
	public Consulta updateConsulta(Consulta consulta, Consulta BodyConsulta) {
		//consulta.setMascotaId(BodyConsulta.getMascotaId());
		consulta.setFecha(BodyConsulta.getFecha());
		consulta.setEnfermedad(BodyConsulta.getEnfermedad());
		return consultaDao.save(consulta);
	}

	@Override
	public List<Consulta> getConsultas() {
		List<Consulta> consulta =  consultaDao.findAll();
		return consulta;	
	}

	@Override
	public ByteArrayInputStream consultaListToExcelFile(List<Consulta> consulta) {
		String[] row_heading = {"consultaId","mascotaId","Enfermedad","Fecha"};
		try{
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Consulta");
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
	        for(int i = 0; i < consulta.size(); i++) {
	        	Row dataRow = sheet.createRow(i + 1);
	        	dataRow.createCell(0).setCellValue(consulta.get(i).getConsultaId());
	        	dataRow.createCell(1).setCellValue(consulta.get(i).getMascotaId());
	        	dataRow.createCell(2).setCellValue(consulta.get(i).getEnfermedad());
	        	dataRow.createCell(3).setCellValue(consulta.get(i).getFecha());
	        }
	        // Making size of column auto resize to fit with data
	        sheet.autoSizeColumn(0);
	        sheet.autoSizeColumn(1);
	        sheet.autoSizeColumn(2);
	        sheet.autoSizeColumn(3);
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);
	        return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		
	}

}
}
