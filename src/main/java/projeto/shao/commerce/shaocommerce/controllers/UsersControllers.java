package projeto.shao.commerce.shaocommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import projeto.shao.commerce.shaocommerce.models.Comerciante;
import projeto.shao.commerce.shaocommerce.repositories.ComercianteRepository;

@Controller
public class UsersControllers {
    @Autowired
	private ComercianteRepository cr;

    @GetMapping ("/")
	public ModelAndView listar() {
		List<Comerciante> comerciantes = cr.findAll();
		ModelAndView mv = new ModelAndView("visitante/vendedores");
		mv.addObject("comerciantes", comerciantes);

		return mv;
	}
    
    @GetMapping("/login")
    public String login(){
        return "login/login";
    }
    
}
