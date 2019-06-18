package tasks.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tasks.modelo.Task;

@Repository
public class TaskDao {

	private EntityManagerFactory factory;
	private EntityManager entityManager;
	
	@Autowired
	public TaskDao(DataSource dataSource) {
		factory = Persistence.createEntityManagerFactory("tasks");
		entityManager = factory.createEntityManager();
	}
	
	public void inserir(Task task) {
		entityManager.getTransaction().begin();
		entityManager.persist(task);
		//entityManager.flush();
		entityManager.getTransaction().commit();
		//entityManager.close();
	}
	
	public List<Task> getTasks() {
		List<Task> tasks = entityManager.createQuery("select t from Task as t").getResultList();		
		//entityManager.close();
		return tasks;
	}
	
	public void exclui(Task task) {
		if (task.getId() == null) {
			throw new IllegalStateException("Id da task não pode ser nula.");
		}
		entityManager.getTransaction().begin();
		entityManager.remove(task);
		entityManager.getTransaction().commit();
		//entityManager.close();
	}
	
	public Task getById(Long id) {
		Task resultTask = entityManager.find(Task.class, id);
		//entityManager.close();
		return resultTask;
	}
	
	public void edita(Task task) {
		entityManager.getTransaction().begin();
		entityManager.merge(task);
		entityManager.getTransaction().commit();
		//entityManager.close();		
	}
	
	public void finaliza(Long id) {
		if (id == null) {
			throw new IllegalStateException("Id da task não pode ser nula.");
		}
		
		Task result = getById(id);
		result.setDataFinalizacao(Calendar.getInstance());
		result.setFinalizada(true);
		edita(result);
	}	
}
	