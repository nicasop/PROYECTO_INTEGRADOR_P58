package com.entities.proyecto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="tipo_usuario",catalog="Base_DataWorld",schema="public")
public class TipoUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_tipo")
	private int codigo_tipo;
	@Column(name = "nombre_tipo")
	private String tipo;
	
	public int getCodigo_tipo() {
		return codigo_tipo;
	}
	public void setCodigo_tipo(int codigo_tipo) {
		this.codigo_tipo = codigo_tipo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	

}
