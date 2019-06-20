package tasks.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tasks.modelo.Usuario;

@Repository
public class UsuarioDao {
	
	@PersistenceContext
	private EntityManager entityManager;
		
	public boolean existeUsuario(Usuario usuario) {
		if (usuario == null) {
			throw new IllegalArgumentException("Usuario não pode ser nulo");
		}
		
		Query query = entityManager.createQuery("select u from Usuario as u where u.login = ?1");
		query.setParameter(1, usuario.getLogin());
		
		boolean result = (query.getSingleResult() != null);
		
		//entityManager.close();
		
		return result;
	}
}
