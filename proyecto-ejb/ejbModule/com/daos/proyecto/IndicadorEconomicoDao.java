package com.daos.proyecto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entities.proyecto.IndicadorEconomico;


@Stateless
public class IndicadorEconomicoDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public IndicadorEconomico buscar (int identi) {
		return em.find(IndicadorEconomico.class, identi);
	}
	
}
