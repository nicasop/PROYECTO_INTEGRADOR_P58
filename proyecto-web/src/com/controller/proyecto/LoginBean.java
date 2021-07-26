package com.controller.proyecto;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.PrimeFacesContext;

import com.daos.proyecto.UsuarioDao;
import com.entities.proyecto.Usuario;

@Named("login")
@ViewScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioDao usuarioDao;

	private Usuario usuario;

	private String[] pagProAd = {"/menuOperador.xhtml","/cambPswOpe.xhtml","/menuRepo.xhtml","/repoOpe.xhtml","/reporte1.xhtml","/reporte2.xhtml",
			"/reporte3.xhtml","/reporte4.xhtml"};
	private String[] pagProOp = {"/menuAdmin.xhtml","/editarPsw.xhtml","/auditoria.xhtml","/menuUusario.xhtml","/editUsuario.xhtml","/registro.xhtml",
			"/bloUsuario.xhtml","/consUsuario.xhtml","/eliUser.xhtml"};

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@PostConstruct
	public void init() {
		usuario = new Usuario();
	}

	public String iniciarSesion() {
		Usuario user;
		String destino = null;
		try {
			user = usuarioDao.verificarUsuario(usuario);
			if (user != null) {
				if (user.getEstado() == 1) {
					PrimeFacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", user);
					if (user.getTipo().getCodigo_tipo() == 1) {
						destino = "admin";
					} else if (user.getTipo().getCodigo_tipo() == 2) {
						destino = "operador";
					}
				} else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Usuario Bloqueado"));
				}

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Credenciales Incorrectas"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Error"));
		}
		return destino;
	}

	//Método de verificación de sesion activa.
	public void verificarSesion() {
		try {
			Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("usuario");
			if (us == null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");
			} else {
				String paginaActual = FacesContext.getCurrentInstance().getViewRoot().getViewId();
				int tipo = us.getTipo().getCodigo_tipo();
				if (tipo == 1) {//administrador
					if(comprobarPaginaAd(paginaActual)) {
						FacesContext.getCurrentInstance().getExternalContext().redirect("menuAdmin.jsf");
					}
				} else if ( tipo == 2) {//Operador
					if(comprobarPaginaOp(paginaActual)) {
						FacesContext.getCurrentInstance().getExternalContext().redirect("menuOperador.jsf");
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void verificarSesionLo() {
		try {
			Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
			if (us != null) {
				if (us.getTipo().getCodigo_tipo() == 1) {
					FacesContext.getCurrentInstance().getExternalContext().redirect("menuAdmin.jsf");
				} else if (us.getTipo().getCodigo_tipo() == 2) {
					FacesContext.getCurrentInstance().getExternalContext().redirect("menuOperador.jsf");
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void cerrarSesion() {
		try {

			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private boolean comprobarPaginaAd(String pagina) {
		for (String nombre : pagProAd) {
			if (nombre.equals(pagina)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean comprobarPaginaOp(String pagina) {
		for (String nombre : pagProOp) {
			if (nombre.equals(pagina)) {
				return true;
			}
		}
		
		return false;
	}
}
