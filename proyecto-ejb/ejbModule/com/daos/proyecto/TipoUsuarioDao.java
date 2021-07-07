package com.daos.proyecto;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.entities.proyecto.TipoUsuario;

@Stateless
public class TipoUsuarioDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<TipoUsuario> getTipos(){
		TypedQuery<TipoUsuario> consulta = em.createNamedQuery("TipoUsuario.todos", TipoUsuario.class);
		return consulta.getResultList();
	}
}
