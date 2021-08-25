package com.jama.api.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


//@Data
@Entity
@Table(name = "consulta")
public class Consulta {
	
	@Id()
	@JsonProperty
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "consultaId")
	private int consultaId;
	
	@Column(name = "mascotaId")
	private int mascotaId;//Id_Mascota;
	
	@Column(name = "Enfermedad")
	private String Enfermedad;

	@Column(name = "Fecha")
	private Date Fecha;

	public int getConsultaId() {
		return consultaId;
	}

	public void setConsultaId(int consultaId) {
		this.consultaId = consultaId;
	}

	public int getMascotaId() {
		return mascotaId;
	}

	public void setMascotaId(int mascotaId) {
		this.mascotaId = mascotaId;
	}

	public String getEnfermedad() {
		return Enfermedad;
	}

	public void setEnfermedad(String enfermedad) {
		Enfermedad = enfermedad;
	}

	public Date getFecha() {
		return Fecha;
	}

	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	
	

	

}
