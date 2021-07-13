package com.graphics.proyecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import com.daos.proyecto.PaisDao;
import com.entities.proyecto.Pais;
import com.entities.proyecto.ValorEconomico;

@Named("comparacion")
@SessionScoped
public class ComparacionesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PaisDao paisDao;
	
	private List<Pais> paises;
	private Pais paisEle;
	private String [] indicadores;
	private int opcion;
	private PieChartModel pastel;
	private List<Vector<Object>> valores;
	private String titulo;

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}
	
	public Pais getPaisEle() {
		return paisEle;
	}

	public void setPaisEle(Pais paisEle) {
		this.paisEle = paisEle;
	}
	
	public int getOpcion() {
		return opcion;
	}

	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}
	
	public PieChartModel getPastel() {
		return pastel;
	}

	public void setPastel(PieChartModel pastel) {
		this.pastel = pastel;
	}
	
	@PostConstruct
	public void init() {
		paises = paisDao.todos();
	}	
	
	private void cargaIndicadores() {
		indicadores = new String[2];
		if (opcion == 1) {
			indicadores[0] = "Electricity - consumption(kWh)";
			indicadores[1] = "Electricity - production(kWh)";
			titulo = "Electricidad Consumo vs Produccion";
		}
		else if (opcion == 2) {
			indicadores[0] = "Internet hosts";
			indicadores[1] = "Internet users";
			titulo = "Intenet hosts vs Usuarios";
		}
	}
	
	private void obtenerValores() {
		valores = new ArrayList<Vector<Object>>();
		
		
		for(ValorEconomico valor : paisEle.getEconomicos()) {
			Vector<Object> datos = new Vector<Object>();
			if (valor.getIndi().getNombre().equals(indicadores[0])) {
				datos.add(indicadores[0]);
				datos.add(valor.getValor());
				valores.add(datos);
			}
			else if (valor.getIndi().getNombre().equals(indicadores[1])) {
				datos.add(indicadores[1]);
				datos.add(valor.getValor());
				valores.add(datos);
			}
			if (valores.size() == 2) {
				break;
			}
		}
	}
	
	private void crearModelo() {
        pastel = new PieChartModel();
        
        if (valores.get(0).get(0).toString().equals(indicadores[0])) {
        	pastel.set(valores.get(0).get(0).toString(), Double.parseDouble(valores.get(0).get(1).toString()));
        	pastel.set(valores.get(1).get(0).toString(), Double.parseDouble(valores.get(1).get(1).toString()));
        }else {
        	pastel.set(valores.get(1).get(0).toString(), Double.parseDouble(valores.get(1).get(1).toString()));
        	pastel.set(valores.get(0).get(0).toString(), Double.parseDouble(valores.get(0).get(1).toString()));
        }
        
        pastel.setTitle(titulo	);
        pastel.setLegendPosition("e");
        pastel.setDiameter(300);
        pastel.setShadow(false);
    }
	
	public void crearGrafico() {
		cargaIndicadores();
		obtenerValores();
		crearModelo();
	}
	
	
}
