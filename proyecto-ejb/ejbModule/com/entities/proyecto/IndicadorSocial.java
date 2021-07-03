package com.entities.proyecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="indicadores_sociales",catalog="dataworld",schema="public")
public class IndicadorSocial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_indicador_social")
	private int identSo;
	@Column(name="nombre_indicador")
	private String nombre;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy ="indi")
	private List<ValorSocial> sociales = new ArrayList<ValorSocial>();
	
	public IndicadorSocial() {}

	public int getidentSo() {
		return identSo;
	}

	public void setidentSo(int identSo) {
		this.identSo = identSo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<ValorSocial> getSociales() {
		return sociales;
	}

	public void setSociales(List<ValorSocial> sociales) {
		this.sociales = sociales;
	}

}
