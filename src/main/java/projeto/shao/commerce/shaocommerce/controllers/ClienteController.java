package projeto.shao.commerce.shaocommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import projeto.shao.commerce.shaocommerce.models.Cliente;
import projeto.shao.commerce.shaocommerce.repositories.ClienteRepository;


@Controller
public class ClienteController {

    @Autowired
    private ClienteRepository cl;

    @GetMapping("/login")
    public String login(){
        return "login";
    }   

    @RequestMapping("/")
    public String index() {
        return "redirect:/produtos";
    }

   
     @GetMapping("/formUser")
    public String formUser(Cliente cliente) {
        return "cadastros/formUser";
    }

    @PostMapping
	public String salvarComerciante(@Valid Cliente cliente, BindingResult result) {

		if (result.hasErrors()) {
			return formUser(cliente);
		}
		

			cl.save(cliente);
			System.out.println("Cliente Salvo!");
		

		return login();
	}


   


}