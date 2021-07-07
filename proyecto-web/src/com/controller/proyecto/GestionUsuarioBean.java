package com.controller.proyecto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.daos.proyecto.TipoUsuarioDao;
import com.daos.proyecto.UsuarioDao;
import com.entities.proyecto.TipoUsuario;
import com.entities.proyecto.Usuario;


@Named("usuario")
@SessionScoped
public class GestionUsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UsuarioDao usuarioDao;
	
	@EJB
	private TipoUsuarioDao tipoUsDao;
	
	private String usuario,nombre,correo,contra,contra1,genero,mensaje;
	private TipoUsuario tipo;
	private Date fechaN;
	private Usuario user,us;
	private boolean renderizar,bloqueo,desbloqueo;
	
	private List<TipoUsuario> tipos;
	private List<Usuario> usuarios;
	
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
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

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public Date getFechaN() {
		return fechaN;
	}

	public void setFechaN(Date fechaN) {
		this.fechaN = fechaN;
	}

	public List<TipoUsuario> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoUsuario> tipos) {
		this.tipos = tipos;
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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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

	public void setBloqueo(boolean bloque) {
		this.bloqueo = bloque;
	}

	public boolean getDesbloqueo() {
		return desbloqueo;
	}

	public void setDesbloqueo(boolean desbloqueo) {
		this.desbloqueo = desbloqueo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	@PostConstruct
	public void init() {
		tipos = tipoUsDao.getTipos();
		usuario = null;
		nombre = null;
		tipo = null;
		correo = null;
		fechaN = null;
		contra = null;
		genero = null;
		user = null;
		usuarios = usuarioDao.getUsuarios();
		renderizar = false;
		bloqueo = false;
		desbloqueo = false;
		us = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	}
	
	public String registrar () {
		Usuario us = new Usuario();
		us.setUsuario(usuario);
		us.setNombreUsuario(nombre);
		us.setTipo(tipo);
		us.setCorreo(correo);
		us.setFechaNa(fechaN);
		us.setGenero(genero);
		us.setContra(contra);
		us.setEstado(1);
		usuarioDao.crear(us);
		
		return "registrado";
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
		renderizar = false;
	}
	
	public void desbloquear() {
		us.setEstado(1);
		usuarioDao.actulizar(us);
		renderizar = false;
	}
	
	public void actualizar() {
		usuarioDao.actulizar(us);
	}
	
}
