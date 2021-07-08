package com.controller.proyecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.daos.proyecto.UsuarioDao;
import com.entities.proyecto.Usuario;

@Named("consulta")
@SessionScoped
public class ConsultarUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioDao usuarioDao;
	
	private int opcion,rol,estado;
	private String genero;
	private boolean pn1,pn2,pn3,tb1,tb2,tb3;
	private List<Usuario> usuarios;
	
	
	public int getOpcion() {
		return opcion;
	}
	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}
	public boolean getPn1() {
		return pn1;
	}
	public void setPn1(boolean pn1) {
		this.pn1 = pn1;
	}
	public boolean getPn2() {
		return pn2;
	}
	public void setPn2(boolean pn2) {
		this.pn2 = pn2;
	}
	public boolean getPn3() {
		return pn3;
	}
	public void setPn3(boolean pn3) {
		this.pn3 = pn3;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public int getRol() {
		return rol;
	}
	public void setRol(int rol) {
		this.rol = rol;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public boolean getTb1() {
		return tb1;
	}
	public void setTb1(boolean tb1) {
		this.tb1 = tb1;
	}
	public boolean getTb2() {
		return tb2;
	}
	public void setTb2(boolean tb2) {
		this.tb2 = tb2;
	}
	public boolean getTb3() {
		return tb3;
	}
	public void setTb3(boolean tb3) {
		this.tb3 = tb3;
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	@PostConstruct
	public void init() {
		pn1 = false;
		pn2 = false;
		pn3 = false;
		tb1 = false;
		tb2 = false;
		tb3 = false;
	}
	
	public void verificarfiltro() {
		if(opcion == 1 ) {
			pn1 = true;
			pn2 = false;
			pn3 = false;
		}else if(opcion == 2 ) {
			pn1 = false;
			pn2 = true;
			pn3 = false;
		}else if(opcion == 3 ) {
			pn1 = false;
			pn2 = false;
			pn3 = true;
		}
		tb1 = false;
		tb2 = false;
		tb3 = false;
	}
	
	public void filtroGenero(){
		List<Usuario> aux = usuarioDao.getUsuarios();
		List<Usuario> resultado = new ArrayList<Usuario>();
		
		for (Usuario user : aux) {
			if(user.getGenero().equals(genero)) {
				resultado.add(user);
			}
		}
		tb1 = true;
		tb2 = false;
		tb3 = false;
		usuarios = resultado;
	}
	
	public void filtroRol(){
		List<Usuario> aux = usuarioDao.getUsuarios();
		List<Usuario> resultado = new ArrayList<Usuario>();
		
		for (Usuario user : aux) {
			if(user.getTipo().getCodigo_tipo() == rol) {
				resultado.add(user);
			}
		}
		tb1 = false;
		tb2 = true;
		tb3 = false;
		usuarios = resultado;
	}
	
	public void filtroEstado(){
		List<Usuario> aux = usuarioDao.getUsuarios();
		List<Usuario> resultado = new ArrayList<Usuario>();
		
		for (Usuario user : aux) {
			if(user.getEstado() == estado) {
				resultado.add(user);
			}
		}
		tb1 = false;
		tb2 = false;
		tb3 = true;
		usuarios = resultado;
	}
	
}
