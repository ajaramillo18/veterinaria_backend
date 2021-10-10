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
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "hospitalizacion")
public class Hospitalizacion {

	@Id()
	@JsonProperty
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hospitalizacion_id")
	private int hospitalizacionId;
	
	@Column(name = "consultaId")
	private int consultaId;
	
	@Column(name = "fecha_llegada")
	private Date fechallegada;
	
	@Column(name = "fecha_salida")
	private Date fechasalida;
	
	@Column(name = "cubiculo")
	private int cubiculo;

	@Transient
	private String estatus;
	
	public String getEstatusMetodo(Hospitalizacion hospitalizacion) {
		String status=null;
		Format formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		String  salida = formatter2.format(hospitalizacion.getFechasalida());
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
	
	public int getHospitalizacionId() {
		return hospitalizacionId;
	}

	public void setHospitalizacionId(int hospitalizacionId) {
		this.hospitalizacionId = hospitalizacionId;
	}

	public int getConsultaId() {
		return consultaId;
	}

	public void setConsultaId(int consultaId) {
		this.consultaId = consultaId;
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
