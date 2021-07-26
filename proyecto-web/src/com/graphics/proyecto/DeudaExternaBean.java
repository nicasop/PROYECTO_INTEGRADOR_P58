package com.graphics.proyecto;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.daos.proyecto.IndicadorEconomicoDao;
import com.entities.proyecto.IndicadorEconomico;
import com.entities.proyecto.ValorEconomico;

@Named("deuda")
@SessionScoped
public class DeudaExternaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private IndicadorEconomicoDao indicadorEconomicoDao;
	
	private IndicadorEconomico indicador;
	private List<Vector<Object>> paisesValores;
	private BarChartModel grafico;
	
	public IndicadorEconomico getIndicador() {
		return indicador;
	}
	public void setIndicador(IndicadorEconomico indicador) {
		this.indicador = indicador;
	}
	public List<Vector<Object>> getPaisesValores() {
		return paisesValores;
	}
	public void setPaisesValores(List<Vector<Object>> paisesValores) {
		this.paisesValores = paisesValores;
	}
	public BarChartModel getGrafico() {
		return grafico;
	}
	public void setGrafico(BarChartModel grafico) {
		this.grafico = grafico;
	}
	
	@PostConstruct
	public void init() {
		indicador = indicadorEconomicoDao.buscar(2);
		paisesValores = getValores();
		cargarGrafico();
	}
	
	public List<Vector<Object>> getValores() {
		List<ValorEconomico> valores = indicador.getEconomicos();
		List<Vector<Object>> valoresFiltro = new Vector<Vector<Object>>();
		for (int i = 0; i < valores.size(); i += 22) {
			Vector<Object> dato = new Vector<Object>();
			dato.add(valores.get(i).getPais().getNombre());//nombre del pais
			dato.add(valores.get(i).getValor());//valor de la deuda externa
			valoresFiltro.add(dato);
		}
		return valoresFiltro;
	}
	
	private List<Vector<Object>> ordenar(List<Vector<Object>> vector){
		Vector<Object> aux;
		List<Vector<Object>> vectorOrdenado = new Vector<Vector<Object>>();
		for(int i = 2; i < vector.size(); i++) {
			for(int j = 0; j < vector.size() - i; j++) {
				if(Double.parseDouble(vector.get(j).get(1).toString()) < Double.parseDouble(vector.get(j+1).get(1).toString())) {
					aux = vector.get(j);
					vector.set(j, vector.get(j+1));
					vector.set(j+1,aux);
				}
			}
		}
		vectorOrdenado = vector;
		return vectorOrdenado;
	}
	
	private void cargarGrafico() {
		grafico = new BarChartModel();
		List<Vector<Object>> valores = ordenar(getValores());
		
		ChartSeries datos = new ChartSeries();
		datos.setLabel("Deuda Externa");
		double max = 0.0;
		for (int i = 0; i < 10; i++) {
			datos.set(valores.get(i).get(0), Double.parseDouble(valores.get(i).get(1).toString()));
			
			if (i == 0) {max = Double.parseDouble(valores.get(i).get(1).toString());}
		}
		
		grafico.addSeries(datos);

		grafico.setTitle("Paises con la mayor cantidad de Deuda Externa");
		grafico.setLegendPosition("e");
		grafico.setStacked(true);

		Axis xAxis = grafico.getAxis(AxisType.X);
		xAxis.setLabel("Paises");
		
		Axis yAxis = grafico.getAxis(AxisType.Y);
		yAxis.setLabel("Monto");
		yAxis.setMin(0);
		yAxis.setMax(max+2000000000+2000000000);
	}

}
