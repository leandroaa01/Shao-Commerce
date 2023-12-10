package projeto.shao.commerce.shaocommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import projeto.shao.commerce.shaocommerce.Enums.Perfil;
import projeto.shao.commerce.shaocommerce.models.Cliente;
import projeto.shao.commerce.shaocommerce.repositories.ClienteRepository;


@Controller
@RequestMapping("/cliente")
public class ClienteController {

    
    @Autowired
    private ClienteRepository cl;
    

    @GetMapping("/login")
    public String login(){
        return "login/login";
    }   

    @RequestMapping("/")
    public String index() {
        return "redirect:/produtos";
    }


   
     @GetMapping("/formUser")
    public ModelAndView formUser(Cliente cliente) {
        ModelAndView mv = new ModelAndView("cadastros/formUser");
        mv.addObject("cliente", cliente);
        Perfil[] profiles = {Perfil.ADMIN, Perfil.CLIENTE};
        mv.addObject("perfils", profiles);
        return mv;
    }

    @PostMapping
	public ModelAndView salvarUser(@Valid Cliente cliente, BindingResult result) {
         ModelAndView mv = new ModelAndView("login");
        mv.addObject("cliente", cliente);

		if (result.hasErrors()) {
			return formUser(cliente);
		}
		
			cl.save(cliente);
			System.out.println("Cliente Salvo!");
		

		return mv;
	}


   


}