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
@Table(name="indicadores_economicos_valores",catalog="BD_world",schema="public")
@IdClass(ValorEconomico.class)
public class ValorEconomico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private int id_pais;
	
	@Id
	private int id_indicador_economico;
	
	
	@Column(name="valor_indicador")
	private double valor;
	
	@ManyToOne
	@JoinColumn(name="id_pais",referencedColumnName = "id_pais", insertable = false, updatable = false)
	private Pais pais;
	
	@ManyToOne
	@JoinColumn(name="id_indicador_economico",referencedColumnName = "id_indicador_economico", insertable = false, updatable = false)
	private IndicadorEconomico indi;
	
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
