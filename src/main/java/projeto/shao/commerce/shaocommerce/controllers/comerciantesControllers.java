package projeto.shao.commerce.shaocommerce.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import projeto.shao.commerce.shaocommerce.models.Comerciante;
import projeto.shao.commerce.shaocommerce.repositories.ComercianteRepository;

@Controller
@RequestMapping("/comerciantes")
public class comerciantesControllers {

	private static String caminhoImagens = "C:\\Users\\70204923476\\Documents\\Imagens\\";

	@Autowired
	private ComercianteRepository cr;

	@GetMapping("/form")
	public String cadastro() {
		return "cadastro/form";
	}

	@PostMapping
	public String cadastrarComerciante(Comerciante comerciante, BindingResult result,
			@RequestParam("file") MultipartFile arquivo) {
		cr.save(comerciante);
		
		try {
			if (!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				Path caminho = Paths.get(caminhoImagens +String.valueOf(comerciante.getId()) +arquivo.getOriginalFilename());
				Files.write(caminho, bytes);
				
				comerciante.setNomeImagem(String.valueOf(comerciante.getId()) + arquivo.getOriginalFilename());
				cr.save(comerciante);
				System.out.println("Caminho completo do arquivo: " + caminho);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Comerciante Salvo");

		return "index";

	}

	@GetMapping("/comerciantes")
	public ModelAndView listar() {
		List<Comerciante> comerciantes = cr.findAll();
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("comerciantes", comerciantes);

		return mv;
	}

}
