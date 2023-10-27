package projeto.shao.commerce.shaocommerce.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @RequestMapping("/")
    public String index() {
        System.out.println("Chamou o Controller!");
        return "redirect:/comerciantes";
    }
    @GetMapping("/form")
	public String form() {
		return "cadastros/form";
	}
}