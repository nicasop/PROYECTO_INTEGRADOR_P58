package com.entities.proyecto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="indicadores_sociales_valores",catalog="Base_DataWorld",schema="public")
@IdClass(ValorSocial.class)
public class ValorSocial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private int id_pais;
	
	@Id
	private int id_indicador_social;
	
	@Column(name="valor_indicador_s")
	private double valor;
	
	@ManyToOne
	@JoinColumn(name="id_pais",referencedColumnName = "id_pais", insertable = false, updatable = false)
	private Pais pais;
	
	@ManyToOne
	@JoinColumn(name="id_indicador_social",referencedColumnName = "id_indicador_social", insertable = false, updatable = false)
	private IndicadorSocial indi;
	
	public ValorSocial() {}
	
	public int getId_pais() {
		return id_pais;
	}

	public void setId_pais(int id_pais) {
		this.id_pais = id_pais;
	}
	
	public int getId_indicador_social() {
		return id_indicador_social;
	}

	public void setId_indicador_social(int id_indicador_social) {
		this.id_indicador_social = id_indicador_social;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public IndicadorSocial getIndi() {
		return indi;
	}

	public void setIndi(IndicadorSocial indi) {
		this.indi = indi;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
}
