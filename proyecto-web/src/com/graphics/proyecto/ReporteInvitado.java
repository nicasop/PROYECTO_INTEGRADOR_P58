package com.graphics.proyecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
//import org.primefaces.model.chart.HorizontalBarChartModel;

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

	private List<Vector<Object>> paisValor;
	private BarChartModel grafico;

	public List<Vector<Object>> getPaises() {
		List<Pais> paises = paisDao.todos();
		
		paisValor = new ArrayList<Vector<Object>>();
		for (Pais p : paises) {
			Vector<Object> datos = new Vector<Object>();
			for (ValorSocial v : p.getSociales()) {
				if (v.getIndi().getNombre().equals("Area(sq km)")) {
					datos.add(p.getNombre());//nombre del pais
					datos.add(v.getValor());//valor del pais
				}
				break;
			}
			paisValor.add(datos);
		}
		initGrafico();
		return paisValor;
	}
	
	private void ordenar() {
		Vector<Object> aux;
		for(int i = 2; i < paisValor.size(); i++) {
			for(int j = 0; j < paisValor.size() - i; j++) {
				if(Double.parseDouble(paisValor.get(j).get(1).toString()) < Double.parseDouble(paisValor.get(j+1).get(1).toString())) {
					aux = paisValor.get(j);
					paisValor.set(j, paisValor.get(j+1));
					paisValor.set(j+1,aux);
				}
			}
		}
	}

	public BarChartModel getGrafico() {
		return grafico;
	}

	public void setGrafico(BarChartModel grafico) {
		this.grafico = grafico;
	}

	private void initGrafico() {
		grafico = new BarChartModel();
		ordenar();
		ChartSeries paises = new ChartSeries();
		paises.setLabel("Area(sq km)");
		double max = 0.0;
		for (int i = 0; i < 10; i++) {
			paises.set(paisValor.get(i).get(0), Double.parseDouble(paisValor.get(i).get(1).toString()));
			
			if (i == 0) {max = Double.parseDouble(paisValor.get(i).get(1).toString());}
		}

		grafico.addSeries(paises);

		grafico.setTitle("Area territorial");
		grafico.setLegendPosition("e");
		grafico.setStacked(true);

		Axis xAxis = grafico.getAxis(AxisType.X);
		xAxis.setLabel("Paises");
		xAxis.setMin(0);
		xAxis.setMax(max+100000);

		Axis yAxis = grafico.getAxis(AxisType.Y);
		yAxis.setLabel("Extensión");
	}

}
