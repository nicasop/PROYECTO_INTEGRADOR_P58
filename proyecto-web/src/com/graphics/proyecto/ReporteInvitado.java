package com.graphics.proyecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

import com.daos.proyecto.PaisDao;
import com.entities.proyecto.Pais;
import com.entities.proyecto.ValorSocial;

@Named("reporte")
@RequestScoped
public class ReporteInvitado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PaisDao paisDao;
	
	private List<Vector<Object>> prueba;
	private HorizontalBarChartModel grafico;
	
	public List<Vector<Object>> getPaises(){
		List<Pais> paises = paisDao.todos();
		prueba = new ArrayList<Vector<Object>>();
		for (Pais p : paises) {
			Vector<Object> datos = new Vector<Object>();
			for (ValorSocial v : p.getSociales()) {
				if (v.getIndi().getNombre().equals("Area(sq km)")) {
					datos.add(p.getNombre());
					datos.add(v.getValor());
				}
				break;
			}
			prueba.add(datos);
		}
		horizontalP();
		return prueba;
	}
	
	
	public HorizontalBarChartModel getGrafico() {
		return grafico;
	}
	
	public void setGrafico(HorizontalBarChartModel grafico) {
		this.grafico = grafico;
	}


	private void horizontalP() {
        grafico = new HorizontalBarChartModel();
 
        ChartSeries paises = new ChartSeries();
        paises.setLabel("Area(sq km)");
        
        for (int i=0;i<10;i++) {
        	paises.set(prueba.get(i).get(0), Double.parseDouble(prueba.get(i).get(1).toString()));
        }
//        for(Vector<Object> pais : prueba) {
//        	paises.set(pais.get(0), Double.parseDouble(pais.get(1).toString()));
//        }
        
        grafico.addSeries(paises);
 
        grafico.setTitle("Area territorial");
        grafico.setLegendPosition("e");
        grafico.setStacked(true);
 
        Axis xAxis = grafico.getAxis(AxisType.X);
        xAxis.setLabel("Extension");
        xAxis.setMin(0);
        xAxis.setMax(3000000);
 
        Axis yAxis = grafico.getAxis(AxisType.Y);
        yAxis.setLabel("Pais");
    }
	
	@PostConstruct
	public void init() {
		getPaises();
	}

}
