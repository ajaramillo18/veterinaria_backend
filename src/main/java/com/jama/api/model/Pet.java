package com.jama.api.model;

import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;



//@Data
@Entity
@Table(name = "mascota")
public class Pet {
	
	@Id()
	@JsonProperty
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mascotaId")
	private int mascotaId;//Id_Mascota;
	
	@Column(name = "nombre")
	private String nombre;
	
    @Column(name = "cliente_Id")
	private int clienteId;//Id_cliente;

	@Column(name = "especie")
	private String especie;

	@Column(name = "raza")
	private String raza;

	@Column(name = "color")
	private String color;

	@Column(name = "nacimiento")
	private Date nacimiento;

	@Column(name = "sexo")
	private String sexo;

	@Column(name = "esterilizado")
	private String esterilizado;
	
	@Column(name = "estatus")
	private char estatus;

	@Transient
	private String edad;
	
	@Transient
	private String clientName;
	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/*
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cliente_id")
    private Client client;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
////*/
	private String calcularEdad(Pet pet) {
		Format formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		String nacimiento = formatter2.format(pet.getNacimiento());
		LocalDate fechaNacimiento = LocalDate.parse(nacimiento);
		Period edadDate = Period.between(fechaNacimiento, LocalDate.now());
		String edad=edadDate.getYears()+" años, "+edadDate.getMonths()+" meses.";
		return edad;
    }
	
    public String getEdad() {
		return calcularEdad(this);
    }
    
    public void setEdad(String edad) {
		this.edad = edad;
	}

	public int getMascotaId() {
		return mascotaId;
	}

	public void setMascotaId(int mascotaId) {
		this.mascotaId = mascotaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Date getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEsterilizado() {
		return esterilizado;
	}

	public void setEsterilizado(String esterilizado) {
		this.esterilizado = esterilizado;
	}

	public char getEstatus() {
		return estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}



}
