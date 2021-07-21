package com.graphics.proyecto;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

@Named("formato")
@SessionScoped
public class FormatoReportesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void preProcessPDF(Object document) throws IOException, DocumentException {
	    final Document pdf = (Document) document;
	    pdf.setPageSize(PageSize.A4.rotate());
	    pdf.open();
	    
	    ServletContext servletContext = (ServletContext)
	    FacesContext.getCurrentInstance().getExternalContext().getContext();
	    String logo = servletContext.getRealPath("/")+"/resources/images/logodoc.png";
	    System.out.println(logo);
	    pdf.add(Image.getInstance(logo));
		pdf.addAuthor("DATAWORLD");
		pdf.addCreationDate();
		pdf.addTitle("INFORME");
	}

}
