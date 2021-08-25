package com.jama.api.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import antlr.collections.List;
import lombok.Data;


//@Data
@Entity
@Table(name = "cliente")
public class Client {
	
	
	@Id()
	@JsonProperty
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cliente_Id")
	private int clienteId;
	
	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	@Column(name = "Nombre")
	private String Nombre;
	
	@Column(name = "Apellido_Paterno")
	private String ApellidoPaterno;
	
	@Column(name = "Apellido_Materno")
	private String ApellidoMaterno;
	
    @Column(name = "Correo")
	private String Correo;

	@Column(name = "Telefono")
	private String Telefono;

	@Column(name = "Direccion")
	private String Direccion;

	@Column(name = "estatus")
	private char estatus;
/*	
	@JsonManagedReference
	@OneToMany(mappedBy="client" )
	private Set<Pet> listaPets;
	  
	public Set<Pet> getListaPets() {
		return listaPets;
	}

	public void setListaPets(Set<Pet> listaPets) {
		this.listaPets = listaPets;
	}
*/
	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getApellidoPaterno() {
		return ApellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		ApellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return ApellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		ApellidoMaterno = apellidoMaterno;
	}

	public String getCorreo() {
		return Correo;
	}

	public void setCorreo(String correo) {
		Correo = correo;
	}

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

	public String getDireccion() {
		return Direccion;
	}

	public void setDireccion(String direccion) {
		Direccion = direccion;
	}

	public char getEstatus() {
		return estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}
	
	
}
