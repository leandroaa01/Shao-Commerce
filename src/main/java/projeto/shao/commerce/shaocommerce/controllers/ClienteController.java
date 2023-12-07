package projeto.shao.commerce.shaocommerce.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import projeto.shao.commerce.Enums.Perfil;
import projeto.shao.commerce.shaocommerce.models.Cliente;
import projeto.shao.commerce.shaocommerce.models.Comerciante;
import projeto.shao.commerce.shaocommerce.repositories.ClienteRepository;
import projeto.shao.commerce.shaocommerce.repositories.ComercianteRepository;


@Controller
public class ClienteController {

    // private static String caminhoImagens = "C:\\Users\\70204923476\\workspaces\\shaocommerce\\src\\main\\resources\\static\\upload\\";
	private static String caminhoImagens = "C:\\Users\\20201204010025\\Desktop\\ProjetoPI\\Shao-commerce\\src\\main\\resources\\static\\upload\\";

    @Autowired
    private ClienteRepository cl;
    @Autowired
	private ComercianteRepository cr;

    @GetMapping("/login")
    public String login(){
        return "login/login";
    }   

    @RequestMapping("/")
    public String index() {
        return "redirect:/produtos";
    }

    @GetMapping("/formComerciante")
	public ModelAndView cadastro(Comerciante comerciante) {
		 ModelAndView mv = new ModelAndView("cadastros/form");
        mv.addObject("comerciante", comerciante);
        Perfil[] profiles = {Perfil.ADMIN, Perfil.COMERCIANTE};
        mv.addObject("perfils", profiles);
        return mv;
		
	}

	@PostMapping
	public ModelAndView salvarComerciante(@Valid Comerciante comerciante, BindingResult result,
			@RequestParam("file") MultipartFile arquivo, @RequestParam("filePath") String filePath, Model model) {
		 ModelAndView mv = new ModelAndView("cadastros/form");
		if (result.hasErrors()) {
			return cadastro(comerciante);
		}
		try {
			if (arquivo != null && !arquivo.isEmpty()) {
				// Verificar se o arquivo é uma imagem
				String contentType = arquivo.getContentType();
				if (contentType != null && contentType.startsWith("image")) {
					// Processar e salvar a nova imagem
					cr.save(comerciante);
					byte[] bytes = arquivo.getBytes();
					String nomeOriginal = arquivo.getOriginalFilename();
					Path caminho = Paths.get(caminhoImagens + String.valueOf(comerciante.getId()) + nomeOriginal);
					Files.write(caminho, bytes);

					comerciante.setNomeImg(String.valueOf(comerciante.getId()) + nomeOriginal);
				} else {
					model.addAttribute("erro", "Apenas arquivos de imagem são permitidos.");
					return mv; // substitua "sua-pagina" pelo nome da sua página Thymeleaf
				}
			} else if (filePath != null && !filePath.isEmpty()) {
				// Se não houver uma nova imagem e há um caminho existente, use o caminho
				// existente
				comerciante.setNomeImg(filePath);
			} else {
				// Se não houver uma nova imagem e nenhum caminho existente, defina como
				// "perfilNulo.png"
				comerciante.setNomeImg("perfilNulo.png");
			}

			cr.save(comerciante);
			System.out.println("Comerciante Salvo");
		} catch (IOException e) {
			e.printStackTrace();
		}
        mv.setViewName("login/login");
		return mv;
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