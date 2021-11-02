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
	private String apellidoPaterno;
	
	@Column(name = "Apellido_Materno")
	private String apellidoMaterno;
	
    @Column(name = "Correo")
	private String correo;

	@Column(name = "Telefono")
	private String telefono;

	@Column(name = "Direccion")
	private String direccion;

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
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public char getEstatus() {
		return estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}
	
	
}
