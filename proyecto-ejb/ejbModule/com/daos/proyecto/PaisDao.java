package com.daos.proyecto;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.entities.proyecto.Pais;

@Stateless
public class PaisDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public Pais buscar (int codigo) {
		return em.find(Pais.class, codigo);
	}
	
	public List<Pais> todos(){
		TypedQuery<Pais> consulta = em.createNamedQuery("Pais.todos", Pais.class);
		return consulta.getResultList();
	}
	
}
