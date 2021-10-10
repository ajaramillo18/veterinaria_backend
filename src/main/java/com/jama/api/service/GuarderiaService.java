package com.jama.api.service;

import java.io.ByteArrayInputStream;
import java.util.List;
import com.jama.api.model.Guarderia;

public interface GuarderiaService {

	Guarderia newGuarderia(Guarderia guarderia);

	List<Guarderia> getGuarderias();
	
	public Guarderia getGuarderia(int Id_guarderia);

	Guarderia updateGuarderia(Guarderia guarderia, Guarderia bodyGuarderia);
	
	public List<Guarderia> getGuarderiasByPet(int Id_Mascota);

	public  ByteArrayInputStream guarderiaListToExcelFile(List<Guarderia> list);
}
