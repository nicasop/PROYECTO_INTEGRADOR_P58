package com.controller.proyecto;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.daos.proyecto.AuditoriaDao;
import com.daos.proyecto.EmailDao;
import com.daos.proyecto.UsuarioDao;
import com.entities.proyecto.Auditoria;
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
	@EJB
	private AuditoriaDao auditoria;
	@EJB
	private EmailDao emailDao;
	
	private Usuario us;
	private String contra, ncontra,ncontra1,mensaje;
	private boolean render, render1,rendercontra;
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
	
	public boolean getRendercontra() {
		return rendercontra;
	}

	public void setRendercontra(boolean rendercontra) {
		this.rendercontra = rendercontra;
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
		rendercontra = true;
		render = false;
		render1 = false;
		codigo = 0;
		clave = -1;
	}

	public void verificarContra() {
		if (us.getContra().equals(contra)) {
			// activa la siguiente vista
			rendercontra = false;
			render = true;
		} else {
			// mensaje de error contrase�a erronea
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Contrase�a Incorrecta"));
		}
	}

	public void verificarContraNueva() {
		if (ncontra.equals(ncontra1)) {
			us.setContra(ncontra);
			clave = aleatorio();
			render = false;
			render1 = true;
			mensaje = "Este es su c�digo de confirmaci�n\n"+clave;
			System.out.println(mensaje);
			enviar();
		} else {
			// mensaje de error
		}
	}

	public void cambiarContrasena() {
		System.out.println(clave);
		System.out.println(codigo);
		if (clave == codigo) {
			usuarioDao.actulizar(us);
			modificarUsuarioAuditoria();
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			//mensaje de error
		}
	}

	private int aleatorio() {
		return (int) (Math.random() * (999999 - 100000 + 1) + 100000);
	}
	
	private void modificarUsuarioAuditoria(){
		System.out.println("si entro");
		List<Auditoria> datos = auditoria.auditoria();
		Auditoria aud = datos.get(datos.size()-1);
		aud.setUsuario(us.getUsuario());
		auditoria.actulizar(aud);
	}
	
	private void enviar() {
		try {
			emailDao.send(us.getCorreo(), "Confirmaci�n de cambio de Contrase�a", mensaje);
		}
		catch (Exception e) {}
	}


}
