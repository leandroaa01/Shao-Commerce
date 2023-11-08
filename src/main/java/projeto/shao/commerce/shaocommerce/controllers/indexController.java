package projeto.shao.commerce.shaocommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import projeto.shao.commerce.shaocommerce.models.Comerciante;
import projeto.shao.commerce.shaocommerce.repositories.ComercianteRepository;

@Controller
public class indexController {

    @RequestMapping("/")
    public String index() {
        System.out.println("Chamou o Controller!");
        return "redirect:/comerciantes";
    }

    @GetMapping("/form")
    public String form(Comerciante comerciante) {
        return "cadastros/form";
    }
@Autowired
	private ComercianteRepository cr;

    @GetMapping("/comerciantes/{id}/produtos")
    public ModelAndView formProduto(@PathVariable Long id) {
        List<Comerciante> comerciantes = cr.findAll();
        ModelAndView mv = new ModelAndView("cadastros/formProdutos");
		mv.addObject("comerciantes", comerciantes);
        mv.addObject("idComerciante", id);

		return mv;
    }

}