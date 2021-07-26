package com.graphics.proyecto;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import com.entities.proyecto.Usuario;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;

@Named("formato")
@RequestScoped
public class FormatoReportesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//formato de los reportes del usuario operador
	public void preProcessPDF(Object document) throws IOException, DocumentException {
	    final Document pdf = (Document) document;
	    pdf.setPageSize(PageSize.A4.rotate());
	    pdf.open();
	    ServletContext servletContext = (ServletContext)
	    FacesContext.getCurrentInstance().getExternalContext().getContext();
	    String logo = servletContext.getRealPath("/")+"/resources/images/logodoc.png";
	    pdf.add(Image.getInstance(logo));
		Phrase titulo = new Phrase("REPORTE\n");
		pdf.add(titulo);
		Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		Phrase usuario = new Phrase(us.getNombreUsuario()+"\n");
		pdf.add(usuario);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		Phrase fecha = new Phrase(dtf.format(LocalDateTime.now()));
		pdf.add(fecha);
	}
	
	
	
	
	//formato de los reportes del usuario invitado
	public void preProcessPDFI(Object document) throws IOException, DocumentException {
	    final Document pdf = (Document) document;
	    pdf.setPageSize(PageSize.A4.rotate());
	    pdf.open();
	    ServletContext servletContext = (ServletContext)
	    FacesContext.getCurrentInstance().getExternalContext().getContext();
	    String logo = servletContext.getRealPath("/")+"/resources/images/logodoc.png";
	    pdf.add(Image.getInstance(logo));
		Phrase titulo = new Phrase("EXTENSI�N TERRITORIAL\n");
		pdf.add(titulo);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		Phrase fecha = new Phrase(dtf.format(LocalDateTime.now()));
		pdf.add(fecha);
	}
}
