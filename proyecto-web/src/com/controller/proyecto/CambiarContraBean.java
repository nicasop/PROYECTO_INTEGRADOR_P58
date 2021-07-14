package com.controller.proyecto;

import java.io.IOException;
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
	private String contra, ncontra,ncontra1;
	private boolean render, render1;
	private int clave, codigo;

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

	public String getNcontra() {
		return ncontra;
	}

	public void setNcontra(String ncontra) {
		this.ncontra = ncontra;
	}

	public String getNcontra1() {
		return ncontra1;
	}

	public void setNcontra1(String ncontra1) {
		this.ncontra1 = ncontra1;
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

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@PostConstruct
	public void init() {
		us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		render = false;
		render1 = false;
		codigo = 0;
	}

	public void verificarContra() {
		if (us.getContra().equals(contra)) {
			// activa la siguiente vista
			render = true;
		} else {
			// mensaje de error contraseña erronea
		}
	}

	public void verificarContraNueva() {
		if (ncontra.equals(ncontra1)) {
			us.setContra(ncontra);
			clave = aleatorio();
			render1 = true;
			System.out.println("SI VALIO");
			System.out.println(clave);
		} else {
			// mensaje de error
		}
	}

	public void cambiarContraseña() {
		System.out.println(clave);
		System.out.println(codigo);
		if (clave == codigo) {
			usuarioDao.actulizar(us);
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			//mensaje de error
			System.out.println("No se cambio");
		}
	}

	private int aleatorio() {
		return (int) (Math.random() * (999999 - 100000 + 1) + 100000);
	}

}
