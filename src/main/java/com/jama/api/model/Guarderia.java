package com.jama.api.model;


import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "guarderia")
public class Guarderia {
	@Id()
	@JsonProperty
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "guarderiaId")
	private int guarderiaId;
	
	@Column(name = "mascotaId")
	private int mascotaId;

	@Column(name = "fecha_llegada")
	private Date fechallegada;
	
	@Column(name = "fecha_salida")
	private Date fechasalida;
	
	@Column(name = "cubiculo")
	private int cubiculo;

	@Transient
	private String estatus;
	
	public String getEstatusMetodo(Guarderia guarderia) {
		String status=null;
		Format formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		String  salida = formatter2.format(guarderia.getFechasalida());
		LocalDate fechalida = LocalDate.parse(salida);
		Period diferencia = Period.between(fechalida, LocalDate.now());
		if(diferencia.getDays()<0||diferencia.getMonths()<0||diferencia.getYears()<0) 
			status="Activa";
		else 
			status="Inactiva";
		if(fechalida.equals(LocalDate.now())) 
			status="Activa";
		return status;
    }
	
	public String getEstatus() {
		return getEstatusMetodo(this);
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public int getGuarderiaId() {
		return guarderiaId;
	}

	public void setGuarderiaId(int guarderiaId) {
		this.guarderiaId = guarderiaId;
	}

	public int getMascotaId() {
		return mascotaId;
	}

	public void setMascotaId(int mascotaId) {
		this.mascotaId = mascotaId;
	}

	public Date getFechallegada() {
		return fechallegada;
	}

	public void setFechallegada(Date fechallegada) {
		this.fechallegada = fechallegada;
	}

	public Date getFechasalida() {
		return fechasalida;
	}

	public void setFechasalida(Date fechasalida) {
		this.fechasalida = fechasalida;
	}

	public int getCubiculo() {
		return cubiculo;
	}

	public void setCubiculo(int cubiculo) {
		this.cubiculo = cubiculo;
	}

	
	
}
