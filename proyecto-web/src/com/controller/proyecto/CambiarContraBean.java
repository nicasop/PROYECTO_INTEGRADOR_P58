package com.controller.proyecto;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.daos.proyecto.UsuarioDao;
import com.entities.proyecto.Usuario;


@Named("cambio")
@SessionScoped
public class CambiarContraBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioDao usuarioDao;
	
	private Usuario us;
	private String contra,contra1;
	private boolean render,render1;
	
	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}

	public String getContra() {
		return contra;
	}

	public void setContra(String contra) {
		this.contra = contra;
	}
	
	public String getContra1() {
		return contra1;
	}

	public void setContra1(String contra1) {
		this.contra1 = contra1;
	}

	public boolean getRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	public boolean getRender1() {
		return render1;
	}

	public void setRender1(boolean render1) {
		this.render1 = render1;
	}
	
	@PostConstruct
	public void init() {
		us = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		render = false;
		render1 = false;
		contra = null;
	}

	public void verificarContra() {
		if (us.getContra().equals(contra)) {
			//activa la siguiente vista
			render = true;
			contra = null;
		}
		else {
			//mensaje de error contraseña erronea
		}
	}
	
	public void verificarContraNueva() {
		if(contra.equals(contra1)) {
			us.setContra(contra1);
			System.out.println("SI VALIO");
		}
		else {
			//mensaje de error
		}
	}

}
