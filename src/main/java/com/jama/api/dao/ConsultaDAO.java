package com.jama.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.jama.api.model.Consulta;
import com.jama.api.model.Pet;

@CrossOrigin({"http://localhost:8088","http://60ed-187-142-69-13.ngrok.io"})
public interface ConsultaDAO extends JpaRepository<Consulta, Integer>{
	List<Consulta> getConsultasByMascotaId(int Id_Mascota);
}
