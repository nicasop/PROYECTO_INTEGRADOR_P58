package com.controller.proyecto;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.daos.proyecto.AuditoriaDao;
import com.daos.proyecto.UsuarioDao;
import com.entities.proyecto.Auditoria;
import com.entities.proyecto.Usuario;


@Named("bloqueo")
@SessionScoped
public class BloqueoUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioDao usuarioDao;
	@EJB
	private AuditoriaDao auditoria;
	
	private String mensaje;
	private Usuario user,us,user1;
	private boolean renderizar,bloqueo,desbloqueo;
	private List<Usuario> usuarios;
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}

	public boolean getRenderizar() {
		return renderizar;
	}

	public void setRenderizar(boolean renderizar) {
		this.renderizar = renderizar;
	}

	public boolean getBloqueo() {
		return bloqueo;
	}

	public void setBloqueo(boolean bloqueo) {
		this.bloqueo = bloqueo;
	}

	public boolean getDesbloqueo() {
		return desbloqueo;
	}

	public void setDesbloqueo(boolean desbloqueo) {
		this.desbloqueo = desbloqueo;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	@PostConstruct
	public void init() {
		usuarios = usuarioDao.getUsuarios();
		user = null;
		renderizar = false;
		bloqueo = false;
		desbloqueo = false;
		user1 = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	}

	public void render() {
		us = user;
		user = null;
		setRenderizar(true);
		if (us.getEstado() == 1) {
			mensaje = "El usuario "+ us.getUsuario() +" esta actualmente activo desea Bloquearlo?";
			bloqueo = true;
			desbloqueo = false;
		}else if(us.getEstado() == 0) {
			mensaje = "El usuario "+ us.getUsuario() +" esta actualmente inactivo desea "
					+ "Desbloquearlo?";
			bloqueo = false;
			desbloqueo = true;
		}
	}
	
	public void bloquear() {
		us.setEstado(0);
		usuarioDao.actulizar(us);
		modificarUsuarioAuditoria();
		renderizar = false;
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Usuario Bloqueado"));
	}
	
	public void desbloquear() {
		us.setEstado(1);
		usuarioDao.actulizar(us);
		modificarUsuarioAuditoria();
		renderizar = false;
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Usuario Desbloqueado"));
	}
	
	private void modificarUsuarioAuditoria(){
		List<Auditoria> datos = auditoria.auditoria();
		Auditoria aud = datos.get(datos.size()-1);
		aud.setUsuario(user1.getUsuario());
		auditoria.actulizar(aud);
	}
	
}
