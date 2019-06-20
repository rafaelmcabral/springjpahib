package tasks.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tasks.modelo.Task;

@Repository
public class TaskDao {

	//private EntityManagerFactory factory;
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void inserir(Task task) {
		//entityManager.getTransaction().begin();
		entityManager.persist(task);
		//entityManager.getTransaction().commit();
		//entityManager.close();
	}
	
	public List<Task> getTasks() {
		List<Task> tasks = entityManager.createQuery("select t from Task as t").getResultList();		
		//entityManager.close();
		return tasks;
	}
	
	@Transactional
	public void exclui(Task task) {
		if (task.getId() == null) {
			throw new IllegalStateException("Id da task não pode ser nula.");
		}
		Task resultTask = entityManager.find(Task.class, task.getId());
		//entityManager.getTransaction().begin();
		entityManager.remove(resultTask);
		//entityManager.getTransaction().commit();
		//entityManager.close();
	}
	
	public Task getById(Long id) {
		Task resultTask = entityManager.find(Task.class, id);
		//entityManager.close();
		return resultTask;
	}
	
	@Transactional
	public void edita(Task task) {
		//entityManager.getTransaction().begin();
		entityManager.merge(task);
		//entityManager.getTransaction().commit();
		//entityManager.close();		
	}
	
	@Transactional
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
	