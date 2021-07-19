package com.controller.proyecto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.daos.proyecto.AuditoriaDao;
import com.entities.proyecto.Auditoria;
import com.entities.proyecto.Usuario;

@Named("auditoria")
@SessionScoped
public class AuditoriaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private AuditoriaDao auditoriaDao;

	private boolean us, fe, ac, tb1;
	private List<Auditoria> informacion;
	private int opcion;
	private String usuario, accion;
	private Date ini, fin;

	public boolean getUs() {
		return us;
	}

	public void setUs(boolean us) {
		this.us = us;
	}

	public boolean getFe() {
		return fe;
	}

	public void setFe(boolean fe) {
		this.fe = fe;
	}

	public boolean getAc() {
		return ac;
	}

	public void setAc(boolean ac) {
		this.ac = ac;
	}

	public boolean getTb1() {
		return tb1;
	}

	public void setTb1(boolean tb1) {
		this.tb1 = tb1;
	}

	public List<Auditoria> getInformacion() {
		return informacion;
	}

	public void setInformacion(List<Auditoria> informacion) {
		this.informacion = informacion;
	}

	public int getOpcion() {
		return opcion;
	}

	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getIni() {
		return ini;
	}

	public void setIni(Date ini) {
		this.ini = ini;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	@PostConstruct
	public void init() {
		us = false;
		ac = false;
		fe = false;
		tb1 = false;
		ini = null;
		fin = null;
		accion = null;
		usuario = null;
	}

	public void eleccion() {
		if (opcion == 1) {
			informacion = auditoriaDao.auditoria();
			init();
			tb1 = true;
		} else if (opcion == 2) {
			init();
			us = true;
			tb1 = false;
		} else if (opcion == 3) {
			init();
			fe = true;
			tb1 = false;
		} else if (opcion == 4) {
			init();
			ac = true;
			tb1 = false;
		}
	}

	public void filtroUsuarios() {
		List<Auditoria> aux = auditoriaDao.auditoria();
		List<Auditoria> resultado = new ArrayList<Auditoria>();

		for (Auditoria fila : aux) {
			if (fila.getUsuario().equals(usuario)) {
				resultado.add(fila);
			}
		}

		tb1 = true;
		informacion = resultado;
	}

	public void filtroFechas() {
		LocalDate auxini = LocalDate.of(ini.getYear()+1900, ini.getMonth()+1, ini.getDate());
		LocalDate auxfin = LocalDate.of(fin.getYear()+1900, fin.getMonth()+1, fin.getDate());
		if(auxini.isBefore(auxfin) || auxini.isEqual(auxfin) ) {
			List<Auditoria> aux = auditoriaDao.auditoria();
			List<Auditoria> resultado = new ArrayList<Auditoria>();
			for (Auditoria fila : aux) {
				LocalDate auxfecha = LocalDate.of(fila.getFecha().getYear()+1900, fila.getFecha().getMonth()+1, fila.getFecha().getDate());
				if ((auxfecha.isAfter(auxini) && auxfecha.isBefore(auxfin)) || auxfecha.isEqual(auxini) || auxfecha.isEqual(auxfin)) {
					resultado.add(fila);
				}
			}

			tb1 = true;
			informacion = resultado;
		}else {
			ini = null;
			fin = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "El rango de fechas fue ingresado de manera erronea"));
		}
	}
	
	public LocalDate formatoFecha(Date fecha) {
		return LocalDate.of(fecha.getYear()+1900, fecha.getMonth()+1, fecha.getDate());
	}

	public void filtroAcciones() {
		List<Auditoria> aux = auditoriaDao.auditoria();
		List<Auditoria> resultado = new ArrayList<Auditoria>();

		for (Auditoria fila : aux) {
			if (fila.getAccion().equals(accion)) {
				resultado.add(fila);
			}
		}

		tb1 = true;
		informacion = resultado;
	}

}
