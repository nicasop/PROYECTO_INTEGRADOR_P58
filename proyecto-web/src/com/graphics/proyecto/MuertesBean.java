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

import com.daos.proyecto.IndicadorSocialDao;
import com.entities.proyecto.IndicadorSocial;
import com.entities.proyecto.ValorSocial;

@Named("muerte")
@SessionScoped
public class MuertesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private  IndicadorSocialDao indicadorSocialDao;
	
	private IndicadorSocial indicador;
	private List<Vector<Object>> paisesValores;
	private BarChartModel mejores,peores;
	
	
	public IndicadorSocial getIndicador() {
		return indicador;
	}

	public void setIndicador(IndicadorSocial indicador) {
		this.indicador = indicador;
	}
	
	public List<Vector<Object>> getPaisesValores() {
		return paisesValores;
	}

	public void setPaisesValores(List<Vector<Object>> paisesValores) {
		this.paisesValores = paisesValores;
	}

	public List<Vector<Object>> getValores(){
		List<ValorSocial> valores = indicador.getSociales();
		List<Vector<Object>> valoresFiltro = new Vector<Vector<Object>>();
		for(int i = 0 ; i < valores.size();i+=22) {
			Vector<Object> dato = new Vector<Object>();
			dato.add(valores.get(i).getPais().getNombre());
			dato.add(valores.get(i).getValor());
			valoresFiltro.add(dato);
		}
		return valoresFiltro;
	}
	
	public BarChartModel getMejores() {
		return mejores;
	}

	public void setMejores(BarChartModel mejores) {
		this.mejores = mejores;
	}

	public BarChartModel getPeores() {
		return peores;
	}

	public void setPeores(BarChartModel peores) {
		this.peores = peores;
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
	
	private List<Vector<Object>> ordenarM(List<Vector<Object>> vector){
		Vector<Object> aux;
		List<Vector<Object>> vectorOrdenado = new Vector<Vector<Object>>();
		for(int i = 2; i < vector.size(); i++) {
			for(int j = 0; j < vector.size() - i; j++) {
				if(Double.parseDouble(vector.get(j).get(1).toString()) > Double.parseDouble(vector.get(j+1).get(1).toString())) {
					aux = vector.get(j);
					vector.set(j, vector.get(j+1));
					vector.set(j+1,aux);
				}
			}
		}
		vectorOrdenado = vector;
		return vectorOrdenado;
	}
	
	@PostConstruct
	public void init() {
		indicador = indicadorSocialDao.buscar(6);
		paisesValores = getValores();
		cargarMejores();
		cargarPeores();
	}
	
	private void cargarMejores() {
		mejores = new BarChartModel();
		List<Vector<Object>> valorMejores = ordenar(getValores());
		
		ChartSeries datos = new ChartSeries();
		datos.setLabel("Muertes por VIH/SIDA");
		double max = 0.0;
		for (int i = 0; i < 10; i++) {
			datos.set(valorMejores.get(i).get(0), Double.parseDouble(valorMejores.get(i).get(1).toString()));
			
			if (i == 0) {max = Double.parseDouble(valorMejores.get(i).get(1).toString());}
		}
		
		mejores.addSeries(datos);

		mejores.setTitle("Paises con la mayor cantidad de muertes por VIH/SIDA");
		mejores.setLegendPosition("e");
		mejores.setStacked(true);

		Axis xAxis = mejores.getAxis(AxisType.X);
		xAxis.setLabel("Paises");
		
		Axis yAxis = mejores.getAxis(AxisType.Y);
		yAxis.setLabel("Muertes");
		yAxis.setMin(0);
		yAxis.setMax(max+100000);
	}
	
	private void cargarPeores() {
		peores = new BarChartModel();
		List<Vector<Object>> valorPeores = ordenarM(getValores());
		
		ChartSeries datos = new ChartSeries();
		datos.setLabel("Muertes por VIH/SIDA");
		double max = 0.0;
		int cont = 0;
		for(Vector<Object> valor : valorPeores) {
			if (Double.parseDouble(valor.get(1).toString()) != 0 ) {
				datos.set(valor.get(0), Double.parseDouble(valor.get(1).toString()));
				cont ++;
			}
			if (cont == 10) {
				max = Double.parseDouble(valor.get(1).toString());
				break;
			}
		}
		
		peores.addSeries(datos);

		peores.setTitle("Paises con la menor cantidad de muertes por VIH/SIDA");
		peores.setLegendPosition("e");
		peores.setStacked(true);

		Axis xAxis = peores.getAxis(AxisType.X);
		xAxis.setLabel("Paises");

		Axis yAxis = peores.getAxis(AxisType.Y);
		yAxis.setLabel("Muertes");
		yAxis.setMin(0);
		yAxis.setMax(max+100000);
	}
}
