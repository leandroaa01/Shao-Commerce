package projeto.shao.commerce.shaocommerce.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import projeto.shao.commerce.shaocommerce.Enums.Perfil;
import projeto.shao.commerce.shaocommerce.Util.PasswordUtil;
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
        Perfil[] profiles = {Perfil.CLIENTE};
        mv.addObject("perfils", profiles);
        return mv;
    }

    @PostMapping
	public ModelAndView salvarUser(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {
         ModelAndView mv = new ModelAndView("login");

         String hashSenha = PasswordUtil.encoder(cliente.getSenha());
        cliente.setSenha(hashSenha);
        mv.addObject("cliente", cliente);

		if (result.hasErrors()) {
			return formUser(cliente);
		}
		
			cl.save(cliente);
			System.out.println("Cliente Salvo!");
			attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
		

		return mv;
	}

    @GetMapping("/editar-perfil")
	public ModelAndView Editacliente(@RequestParam("id") Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Cliente> opt = cl.findById(id);
		if (opt.isEmpty()) {
			md.setViewName("redirect:/clientes");
			return md;
		}
		Cliente cliente = opt.get();
		md.setViewName("cadastros/editUser");
		md.addObject("cliente", cliente);
		Perfil[] profiles = {Perfil.CLIENTE};
        md.addObject("perfils", profiles);
		md.addObject("senha", cliente.getSenha());

		return md;
	}

	@PostMapping("/editar-perfil")
	public ModelAndView salvarEdicaocliente(@Valid Cliente cliente, BindingResult result,
     Model model, RedirectAttributes attributes) {
    ModelAndView mv = new ModelAndView("cadastros/formUser");
    if (result.hasErrors()) {
        return new ModelAndView("cadastro/formUser");
    }
        cl.save(cliente);
        System.out.println("Atualização Salvo");
		attributes.addFlashAttribute("mensagem", "Atualização salvo com sucesso!");
    
    mv.setViewName("home/index");
    return mv;

}

@GetMapping("/{id}/remover")
	public String apagarCliente(@PathVariable Long id, RedirectAttributes attributes) {

		Optional<Cliente> opt = cl.findById(id);

		if (opt.isPresent()) {
			Cliente cliente = opt.get();

			cl.delete(cliente);
			attributes.addFlashAttribute("mensagem", "Cliente deletado salvo com sucesso!");
		}
		
		return "redirect:/admin/clientes";
	}

	

	@GetMapping("/{idCliente}/selecionar")
	public ModelAndView selecionarCliente(@PathVariable Long idCliente) {
		ModelAndView md = new ModelAndView("cadastros/editUser");
		Optional<Cliente> opt = cl.findById(idCliente);
		if (opt.isEmpty()) {
			md.setViewName("redirect:/Clientes");
			return md;
		}
		Cliente Cliente = opt.get();
		md.setViewName("cadastros/editUser");
		md.addObject("Cliente", Cliente);
		Perfil[] profiles = {Perfil.CLIENTE};
        md.addObject("perfils", profiles);
		md.addObject("senha", Cliente.getSenha());

		return md;
	}


   


}