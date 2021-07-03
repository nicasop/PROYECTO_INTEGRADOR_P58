package com.entities.proyecto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="indicadores_economicos_valores",catalog="Base_DataWorld",schema="public")
public class ValorEconomico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="valor_indicador_e")
	private double valor;
	
	@ManyToOne
	@JoinColumn(name="id_indicador_economico")
	private IndicadorEconomico indi;
	
	@ManyToOne
	@JoinColumn(name="id_pais")
	private Pais pais;
	
	public ValorEconomico() {}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public IndicadorEconomico getIndi() {
		return indi;
	}

	public void setIndi(IndicadorEconomico indi) {
		this.indi = indi;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	
}
