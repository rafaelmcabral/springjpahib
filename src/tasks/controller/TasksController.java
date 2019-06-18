package tasks.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import tasks.dao.TaskDao;
import tasks.dao.UsuarioDao;
import tasks.modelo.Task;

@Controller
public class TasksController {
	
	private final TaskDao taskDao;
	
	@Autowired
	public TasksController(TaskDao dao) {
		// TODO Auto-generated constructor stub
		this.taskDao = dao;
	}
	
	@RequestMapping("novatask")
	public String form() {
		return "/form-tasks";
	}
	
	@RequestMapping("cadastratask")
	public String cadastra(@Valid Task task, BindingResult result) {
		if (result.hasFieldErrors("descricao")) {
			return "/form-tasks";
		}
		System.out.println("Descricao = " + task.getDescricao());
		taskDao.inserir(task);
		return "/task-cadastrada";
	}
	
	@RequestMapping("gettasks")
	//primeira opção usando Model And View
	public String getTasks(Model model) {
		model.addAttribute("tasks", taskDao.getTasks());
		return "/get-tasks-ajax";
	}
	
	@RequestMapping("excluitask")
	public String exclui(Task task) {
		taskDao.exclui(task);
		//redirecionamento client side
		return "redirect:gettasks";
//		redirecionamento server side
//		return "forward:gettasks";
	}
	
	@RequestMapping("buscartask")
	public String busca(Long id, Model model) {
		model.addAttribute("task", taskDao.getById(id));
		return "/busca-task";
	}
	
	@RequestMapping("editatask")
	public String edita(Task task) {
		taskDao.edita(task);
		return "redirect:gettasks";
	}
	
//	@ResponseBody
	@RequestMapping("finalizatask")
	public String finaliza(Long id, Model model) {
		taskDao.finaliza(id);
		model.addAttribute("task", taskDao.getById(id));
		return "/data-finalizada";
	}
}
