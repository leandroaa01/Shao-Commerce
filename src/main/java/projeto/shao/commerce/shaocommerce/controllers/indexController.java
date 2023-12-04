package projeto.shao.commerce.shaocommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import projeto.shao.commerce.shaocommerce.models.Comerciante;
import projeto.shao.commerce.shaocommerce.models.Produto;

@Controller
public class indexController {

    @RequestMapping("/")
    public String index() {
        return "redirect:/comerciantes";
    }

    @GetMapping("/form")
    public String form(Comerciante comerciante) {
        return "cadastros/form";
    }


    @GetMapping("/comerciantes/{id}/produtos")
    public String formProduto(@PathVariable Long id, Produto produto) {
        return "cadastros/formProdutos";
       

		
    }

}