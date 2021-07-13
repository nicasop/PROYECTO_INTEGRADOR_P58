package com.entities.proyecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="pais",catalog="Base_DataWorld",schema="public")
@NamedQueries({
	@NamedQuery(name="Pais.todos", query = "Select p from Pais p")
})
public class Pais implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_pais")
	private int idenPais;
	@Column(name="nombre_pais")
	private String nombre;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy ="pais", cascade = CascadeType.ALL)
	private Set<ValorEconomico> economicos = new HashSet<ValorEconomico>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy ="pais", cascade = CascadeType.ALL)
	private List<ValorSocial> sociales = new ArrayList<ValorSocial>();
	
	public Pais() {}

	public int getidenPais() {
		return idenPais;
	}

	public void setidenPais(int idenPais) {
		this.idenPais = idenPais;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
//	public List<ValorEconomico> getEconomicos() {
//		return economicos;
//	}
//
//	public void setEconomicos(List<ValorEconomico> economicos) {
//		this.economicos = economicos;
//	}

	public Set<ValorEconomico> getEconomicos() {
		return economicos;
	}

	public void setEconomicos(Set<ValorEconomico> economicos) {
		this.economicos = economicos;
	}

	public List<ValorSocial> getSociales() {
		return sociales;
	}

	public void setSociales(List<ValorSocial> sociales) {
		this.sociales = sociales;
	}
	
}
