package projeto.shao.commerce.shaocommerce.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import projeto.shao.commerce.shaocommerce.models.Comerciante;
import projeto.shao.commerce.shaocommerce.models.Produto;
import projeto.shao.commerce.shaocommerce.repositories.ComercianteRepository;
import projeto.shao.commerce.shaocommerce.repositories.ProdutoRepository;

@Controller
@RequestMapping("/comerciantes")
public class comerciantesControllers {

	private static String caminhoImagens = "C:\\Users\\70204923476\\workspaces\\shaocommerce\\src\\main\\resources\\static\\upload\\";
	private static String caminhoImagensProduto = "C:\\Users\\70204923476\\workspaces\\shaocommerce\\src\\main\\resources\\static\\uploadProduto\\";

	@Autowired
	private ComercianteRepository cr;

	@Autowired
	private ProdutoRepository pr;

	@GetMapping("/form")
	public String cadastro() {
		return "cadastro/form";
	}

	@PostMapping
	public String cadastrarComerciante(Comerciante comerciante, BindingResult result,
			@RequestParam("file") MultipartFile arquivo) {
		cr.saveAndFlush(comerciante);

		try {
			if (!arquivo.isEmpty() && arquivo != null) {
				byte[] bytes = arquivo.getBytes();
				String nomeOriginal = arquivo.getOriginalFilename(); // Obtenha o nome original do arquivo
				Path caminho = Paths.get(caminhoImagens + nomeOriginal); // Use o nome original do arquivo
				Files.write(caminho, bytes);

				comerciante.setNomeImg(nomeOriginal); // Defina o nome da imagem como o nome original
				cr.saveAndFlush(comerciante);
				System.out.println("Caminho completo do arquivo: " + caminho);
			}else{
				comerciante.setNomeImg("perfilNulo.png");
				cr.save(comerciante);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Comerciante Salvo");

		return "redirect:/comerciantes";
	}

	@GetMapping
	public ModelAndView listar() {
		List<Comerciante> comerciantes = cr.findAll();
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("comerciantes", comerciantes);

		return mv;
	}

	@GetMapping("/{id}")
	public ModelAndView verProdutos(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Comerciante> opt = cr.findById(id);

		if (opt.isEmpty()) {
			md.setViewName("redirect:/comerciantes");
			return md;
		}
		md.setViewName("/produtos");
		Comerciante comerciante = opt.get();

		md.addObject("comerciante", comerciante);

		return md;
	}

	@PostMapping("/{idComerciante}")
	public String cadastrarProduto(@PathVariable Long idComerciante, Produto produto, BindingResult result,
			@RequestParam("file") MultipartFile arquivo) {

		Optional<Comerciante> opt = cr.findById(idComerciante);
		if (opt.isEmpty()) {
			return "redirect:/comerciantes";
		}
		Comerciante comerciante = opt.get();

		produto.setComerciante(comerciante);

		pr.save(produto);
		try {
			if (!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				String nomeOriginal = arquivo.getOriginalFilename(); // Obtenha o nome original do arquivo
				Path caminho = Paths.get(caminhoImagensProduto + nomeOriginal); // Use o nome original do arquivo
				Files.write(caminho, bytes);

				produto.setNomeImg(nomeOriginal); // Define o nome da imagem como o nome original
				pr.saveAndFlush(produto);
				System.out.println("Caminho completo do arquivo: " + caminho);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Comerciante Salvo");

		System.out.println("Id do comerciante:" + idComerciante);

		

		return "redirect:/comerciantes/";

	}

}
