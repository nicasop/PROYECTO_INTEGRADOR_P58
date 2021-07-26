package com.controller.proyecto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.daos.proyecto.AuditoriaDao;
import com.daos.proyecto.TipoUsuarioDao;
import com.daos.proyecto.UsuarioDao;
import com.entities.proyecto.Auditoria;
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

	@EJB
	private AuditoriaDao auditoria;

	private String usuario, nombre, correo, contra, contra1, genero, mensaje;
	private TipoUsuario tipo;
	private Date fechaN;
	private Usuario us, user;
	private List<TipoUsuario> tipos;
	private List<Usuario> usuarios;
	private boolean render;

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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public boolean getRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
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
		usuarios = usuarioDao.getUsuarios();
		usuario = null;
		nombre = null;
		tipo = null;
		correo = null;
		fechaN = null;
		contra = null;
		genero = null;
		render = false;
		us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	}

	public void mostrar() {
		render = true;
		mensaje = "Esta seguro que desea eliminar al usuario " + user.getUsuario() + "?";
	}

	public String registrar() {

		String respuesta = null;
		if (usuarioDao.verificarNombreUser(usuario)) {
			if (contra.equals(contra1)) {
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
				init();
				respuesta = "registrado";
				modificarUsuarioAuditoria();
			} else {
				// mensaje de contraseña erronea
			}
		}else {
			usuario = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "El nombre de usuario ya existe"));
		}

		return respuesta;
	}

	public void actualizar() {
		usuarioDao.actulizar(us);
		modificarUsuarioAuditoria();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Usuario Actualizado"));
	}

	public void eliminar() {
		usuarioDao.borrar(user.getIdentificador());
		user = null;
		render = false;
		modificarUsuarioAuditoria();
	}

	private void modificarUsuarioAuditoria() {
		List<Auditoria> datos = auditoria.auditoria();
		Auditoria aud = datos.get(datos.size() - 1);
		aud.setUsuario(us.getUsuario());
		auditoria.actulizar(aud);
	}
}
