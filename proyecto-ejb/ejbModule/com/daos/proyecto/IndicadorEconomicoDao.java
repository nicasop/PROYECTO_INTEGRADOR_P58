package com.daos.proyecto;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.entities.proyecto.IndicadorEconomico;
import com.entities.proyecto.IndicadorSocial;


@Stateless
public class IndicadorEconomicoDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public IndicadorEconomico buscar (int identi) {
		return em.find(IndicadorEconomico.class, identi);
	}
	
	public List<IndicadorEconomico> todos(){
		TypedQuery<IndicadorEconomico> consulta = em.createNamedQuery("IndicadorEconomico.todos", IndicadorEconomico.class);
		return consulta.getResultList();
	}
	
}
