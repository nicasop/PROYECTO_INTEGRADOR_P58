package com.graphics.proyecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

	private List<Vector<Object>> paisValor;
	private HorizontalBarChartModel grafico;

	public List<Vector<Object>> getPaises() {
		List<Pais> paises = paisDao.todos();
		paisValor = new ArrayList<Vector<Object>>();
		for (Pais p : paises) {
			Vector<Object> datos = new Vector<Object>();
			for (ValorSocial v : p.getSociales()) {
				if (v.getIndi().getNombre().equals("Area(sq km)")) {
					datos.add(p.getNombre());
					datos.add(v.getValor());
				}
				break;
			}
			paisValor.add(datos);
		}
		ordenar();
		horizontalP();
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

//	private List<Vector<Object>> burbuja(List<Vector<Object>>) {
//		int auxiliar;
//		int[] arregloOrdenado;
//		for (int i = 2; i < arreglo.length; i++) {
//			for (int j = 0; j < arreglo.length - i; j++) {
//				if (arreglo[j] > arreglo[j + 1]) {
//					auxiliar = arreglo[j];
//					arreglo[j] = arreglo[j + 1];
//					arreglo[j + 1] = auxiliar;
//				}
//			}
//		}
//		arregloOrdenado = arreglo;
//		return arregloOrdenado;
//	}
	
//	public static int[] burbuja(int[] arreglo)
//    {
//      int auxiliar;
//      int[] arregloOrdenado;
//      for(int i = 2; i < arreglo.length; i++)
//      {
//        for(int j = 0;j < arreglo.length-i;j++)
//        {
//          if(arreglo[j] > arreglo[j+1])
//          {
//            auxiliar = arreglo[j];
//            arreglo[j] = arreglo[j+1];
//            arreglo[j+1] = auxiliar;
//          }   
//        }
//      }
//      arregloOrdenado = arreglo;
//      return arregloOrdenado;
//    }
	

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

		for (int i = 0; i < 10; i++) {
			paises.set(paisValor.get(i).get(0), Double.parseDouble(paisValor.get(i).get(1).toString()));
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

//	@PostConstruct
//	public void init() {
//		getPaises();
//	}

}
