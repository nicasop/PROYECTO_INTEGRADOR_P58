package com.daos.proyecto;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.entities.proyecto.IndicadorSocial;

@Stateless
public class IndicadorSocialDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public IndicadorSocial buscar (int identi) {
		return em.find(IndicadorSocial.class, identi);
	}
	
	public List<IndicadorSocial> todos(){
		TypedQuery<IndicadorSocial> consulta = em.createNamedQuery("IndicadorSocial.todos", IndicadorSocial.class);
		return consulta.getResultList();
	}
	
}
