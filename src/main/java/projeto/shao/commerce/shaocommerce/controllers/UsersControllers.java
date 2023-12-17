package projeto.shao.commerce.shaocommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import projeto.shao.commerce.shaocommerce.Enums.Perfil;
import projeto.shao.commerce.shaocommerce.models.Cliente;
import projeto.shao.commerce.shaocommerce.models.Comerciante;
import projeto.shao.commerce.shaocommerce.repositories.ComercianteRepository;

@Controller
public class UsersControllers {
    @Autowired
	private ComercianteRepository cr;

    @GetMapping ("/")
	public ModelAndView listar() {
		List<Comerciante> comerciantes = cr.findAll();
		ModelAndView mv = new ModelAndView("public/vendedores");
		mv.addObject("comerciantes", comerciantes);

		return mv;
	}
    
    @GetMapping("/login")
    public String login(){
        return "secure/login";
    }

	@GetMapping("/admin")
    public String admin(){
        return "admin/admin";
    }

	 @GetMapping("/Comerciante")
	public ModelAndView cadastro(Comerciante comerciante) {
		 ModelAndView mv = new ModelAndView("admin/comerciante");
        mv.addObject("comerciante", comerciante);
        Perfil[] profiles = {Perfil.COMERCIANTE};
        mv.addObject("perfils", profiles);
        return mv;
		
	} @GetMapping("/user")
    public ModelAndView formUser(Cliente cliente) {
        ModelAndView mv = new ModelAndView("admin/cliente");
        mv.addObject("cliente", cliente);
        Perfil[] profiles = {Perfil.CLIENTE};
        mv.addObject("perfils", profiles);
        return mv;
    }
    
}
