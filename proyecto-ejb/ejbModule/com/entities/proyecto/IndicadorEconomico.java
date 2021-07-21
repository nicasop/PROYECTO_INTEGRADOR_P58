package com.entities.proyecto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="indicadores_economicos",catalog="BD_world",schema="public")
public class IndicadorEconomico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_indicador_economico")
	private int identEco;
	@Column(name="nombre_indicador")
	private String nombre;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy ="indi", cascade = CascadeType.ALL)
	private List<ValorEconomico> economicos;
	
	public IndicadorEconomico() {}

	public int getidentEco() {
		return identEco;
	}

	public void setidentEco(int identEco) {
		this.identEco = identEco;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<ValorEconomico> getEconomicos() {
		return economicos;
	}

	public void setEconomicos(List<ValorEconomico> economicos) {
		this.economicos = economicos;
	}
	
	

}
