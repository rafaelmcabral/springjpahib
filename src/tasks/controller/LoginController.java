package tasks.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import tasks.dao.UsuarioDao;
import tasks.modelo.Usuario;

@Controller
public class LoginController {

	@Autowired
	private UsuarioDao usuarioDao;
	
	@RequestMapping("formlogin")
	public String formLogin() {
		return "/form-login";
	}
	
	@RequestMapping("getlogin")
	public String getLogin(Usuario usuario, HttpSession session) {
		if (usuarioDao.existeUsuario(usuario)) {
			session.setAttribute("usuariologado", usuario);
			return "/index";
		}
		return "redirect:formlogin";
	}
	
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:formlogin";
	}
}
