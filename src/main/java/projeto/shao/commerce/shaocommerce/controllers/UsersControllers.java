package projeto.shao.commerce.shaocommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import projeto.shao.commerce.shaocommerce.Enums.Perfil;
import projeto.shao.commerce.shaocommerce.models.Cliente;
import projeto.shao.commerce.shaocommerce.models.Comerciante;
// import projeto.shao.commerce.shaocommerce.models.ResetPasswordUserDao;
import projeto.shao.commerce.shaocommerce.repositories.ClienteRepository;
import projeto.shao.commerce.shaocommerce.repositories.ComercianteRepository;

@Controller
public class UsersControllers {
    @Autowired
	private ComercianteRepository cr;
    @Autowired
    private ClienteRepository cl;

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

   
    // @GetMapping("/redefinir-senha")
    // public ModelAndView resetPassword(Principal principal){
    //     ModelAndView mv =  new ModelAndView("secure/trocaSenha");
    //     Cliente clientAuth = cl.findByEmail(principal.getName());
        
    //     // Verifica se o cliente foi encontrado antes de prosseguir
    //     if (clientAuth != null) {
    //         ResetPasswordUserDao currentSenhaDAO = new ResetPasswordUserDao(clientAuth.getSenha());
    //         mv.addObject("senhaAtual", currentSenhaDAO);
    //         mv.addObject("resetPasswordUserDAO", new ResetPasswordUserDao());
    //     } else {
    //         // Trate o caso em que não há cliente com o email fornecido.
    //         // Pode ser um redirecionamento, mensagem de erro, etc.
    //     }
    
    //     return mv;
    // }
    
    // @PostMapping("/redefinir-senha")
    // public String resetPassword(ResetPasswordUserDao formularioUserDAO, Principal principal){
    //     Cliente clientAuth = cl.findByEmail(principal.getName());
        
    //     // Verifica se o cliente foi encontrado antes de prosseguir
    //     if (clientAuth != null && PasswordUtil.matchersPassword(formularioUserDAO.getSenhaAtual(), clientAuth.getSenha())) {
    //         clientAuth.setSenha(PasswordUtil.encoder(formularioUserDAO.getNovaSenha()));
    //         clientAuth.setPerfil(Perfil.CLIENTE);
    //         cl.save(clientAuth);
    //     }
        
    //     return "redirect:/produtos";
    // }
    
    
}
