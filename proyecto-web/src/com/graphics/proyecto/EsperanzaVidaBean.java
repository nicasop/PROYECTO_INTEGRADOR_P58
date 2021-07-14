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
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import com.daos.proyecto.IndicadorSocialDao;
import com.entities.proyecto.IndicadorSocial;
import com.entities.proyecto.ValorSocial;

@Named("esperanzaVida")
@SessionScoped
public class EsperanzaVidaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private IndicadorSocialDao indicadorSocialDao;

	private IndicadorSocial indicador;
	private List<Vector<Object>> paisesValores;
	private LineChartModel grafico;

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

	public LineChartModel getGrafico() {
		return grafico;
	}

	public void setGrafico(LineChartModel grafico) {
		this.grafico = grafico;
	}

	public List<Vector<Object>> getValores() {
		List<ValorSocial> valores = indicador.getSociales();
		List<Vector<Object>> valoresFiltro = new Vector<Vector<Object>>();
		for (int i = 0; i < valores.size(); i += 22) {
			Vector<Object> dato = new Vector<Object>();
			dato.add(valores.get(i).getPais().getNombre());
			dato.add(valores.get(i).getValor());
			valoresFiltro.add(dato);
		}
		return valoresFiltro;
	}

	private List<Vector<Object>> ordenar(List<Vector<Object>> vector) {
		Vector<Object> aux;
		List<Vector<Object>> vectorOrdenado = new Vector<Vector<Object>>();
		for (int i = 2; i < vector.size(); i++) {
			for (int j = 0; j < vector.size() - i; j++) {
				if (Double.parseDouble(vector.get(j).get(1).toString()) < Double
						.parseDouble(vector.get(j + 1).get(1).toString())) {
					aux = vector.get(j);
					vector.set(j, vector.get(j + 1));
					vector.set(j + 1, aux);
				}
			}
		}
		vectorOrdenado = vector;
		return vectorOrdenado;
	}

	private void cargarGrafico() {
		grafico = new LineChartModel();
		List<Vector<Object>> valores = ordenar(getValores());
//		List<Vector<Object>> valores = paisesValores;
		ChartSeries datos = new ChartSeries();
		datos.setLabel("Esperanza de vida");
		double max = 0.0;
		for (int i = 0; i < 10; i++) {
			datos.set(valores.get(i).get(0), Double.parseDouble(valores.get(i).get(1).toString()));

			if (i == 0) {
				max = Double.parseDouble(valores.get(i).get(1).toString());
			}
		}

		grafico.addSeries(datos);
		grafico.setTitle("Paises con la esperanza de vida mas alta");
		grafico.setLegendPosition("e");
		grafico.setShowPointLabels(true);
		grafico.getAxes().put(AxisType.X, new CategoryAxis("Paises"));
		Axis yAxis = grafico.getAxis(AxisType.Y);
		yAxis.setLabel("Años");
		yAxis.setMin(0);
//		yAxis.setMax(max + 20);
		yAxis.setMax(90);
	}

	@PostConstruct
	public void init() {
		indicador = indicadorSocialDao.buscar(16);
		paisesValores = getValores();
		cargarGrafico();
//		cargarMejores();
//		cargarPeores();
	}

}
