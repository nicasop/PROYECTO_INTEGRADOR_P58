package com.daos.proyecto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entities.proyecto.IndicadorSocial;

@Stateless
public class IndicadorSocialDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public IndicadorSocial buscar (String nombre) {
		return em.find(IndicadorSocial.class, nombre);
	}
	
}
