package projeto.shao.commerce.shaocommerce.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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
import projeto.shao.commerce.shaocommerce.Enums.Perfil;
import projeto.shao.commerce.shaocommerce.Util.PasswordUtil;
import projeto.shao.commerce.shaocommerce.models.Cliente;
import projeto.shao.commerce.shaocommerce.models.Comerciante;
import projeto.shao.commerce.shaocommerce.repositories.ClienteRepository;
import projeto.shao.commerce.shaocommerce.repositories.ComercianteRepository;

@Controller
@RequestMapping("/admin")
public class AdminControlller {

     // private static String caminhoImagens = "C:\\Users\\70204923476\\workspaces\\shaocommerce\\src\\main\\resources\\static\\upload\\";
	private static String caminhoImagens = "D:/Usuario/Shao-commerce/src/main/resources/static/upload/";
	//private static String caminhoImagens = "C:\\Users\\20201204010025\\Desktop\\ProjetoPI\\Shao-commerce\\src\\main\\resources\\static\\upload\\";
    @Autowired
    private ClienteRepository ad;
    @Autowired
	private ComercianteRepository adm;

     @GetMapping("/admin")
    public ModelAndView admin(Cliente admin) {
        ModelAndView mv = new ModelAndView("admin/admin");
        mv.addObject("admin", admin);
        Perfil[] profiles = {Perfil.ADMIN};
        mv.addObject("perfils", profiles);
        return mv;
    }

    @PostMapping("/")
	public ModelAndView admin(@Valid Cliente admin, BindingResult result) {
         ModelAndView mv = new ModelAndView("login");

         String hashSenha = PasswordUtil.encoder(admin.getSenha());
        admin.setSenha(hashSenha);
        mv.addObject("admin", admin);

		if (result.hasErrors()) {
			return admin(admin);
		}
		
			ad.save(admin);
			System.out.println("admin Salvo!");
		

		return mv;
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

	@PostMapping("/comerciante")
	public ModelAndView salvarComerciante(@Valid Comerciante comerciante, BindingResult result,
			@RequestParam("file") MultipartFile arquivo, @RequestParam String filePath, Model model) {
		String hashSenha = PasswordUtil.encoder(comerciante.getSenha());
        comerciante.setSenha(hashSenha);
		 ModelAndView mv = new ModelAndView("secure/login");
		try {
			if (arquivo != null && !arquivo.isEmpty()) {
				// Verificar se o arquivo é uma imagem
				String contentType = arquivo.getContentType();
				if (contentType != null && contentType.startsWith("image")) {
					// Processar e salvar a nova imagem
					adm.save(comerciante);
					byte[] bytes = arquivo.getBytes();
					String nomeOriginal = arquivo.getOriginalFilename();
					Path caminho = Paths.get(caminhoImagens + String.valueOf(comerciante.getId()) + nomeOriginal);
					Files.write(caminho, bytes);

					comerciante.setNomeImg(String.valueOf(comerciante.getId()) + nomeOriginal);
				} else {
					model.addAttribute("erro", "Apenas arquivos de imagem são permitidos.");
					return cadastro(comerciante); // substitua "sua-pagina" pelo nome da sua página Thymeleaf
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

			adm.save(comerciante);
			System.out.println("Comerciante Salvo");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return listarClientes();
	}

   
    

    @PostMapping("/user")
	public ModelAndView salvarUser(@Valid Cliente cliente, BindingResult result) {
          ModelAndView mv = new ModelAndView("admin/cliente");
		  String hashSenha = PasswordUtil.encoder(cliente.getSenha());
        cliente.setSenha(hashSenha);
        mv.addObject("cliente", cliente);

		if (result.hasErrors()) {
			return formUser(cliente);
		}
		
			ad.save(cliente);
			System.out.println("Cliente Salvo!");
		

		 
		return listarComerciantes();
	}


	@GetMapping("/comerciantes")
	public ModelAndView listarComerciantes() {
		List<Comerciante> comerciantes = adm.findAll();
		ModelAndView mv = new ModelAndView("admin/listaUser");
		mv.addObject("comerciantes", comerciantes);

		return mv;
	}
	@GetMapping("/clientes")
	public ModelAndView listarClientes() {
		List<Cliente> clientes = ad.findAll();
		ModelAndView mv = new ModelAndView("admin/listaCliente");
		mv.addObject("clientes", clientes);

		return mv;
	}

}
    

