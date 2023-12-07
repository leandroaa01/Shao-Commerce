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
@RequestMapping("/produtos")
public class ProdutosController {
	// private static String caminhoImagensProduto = "C:\\Users\\70204923476\\workspaces\\shaocommerce\\src\\main\\resources\\static\\uploadProduto\\";
	private static String caminhoImagensProduto = "C:\\Users\\20201204010025\\Desktop\\ProjetoPI\\Shao-commerce\\src\\main\\resources\\static\\uploadProduto\\";

	@Autowired
	private ComercianteRepository cr;

	@Autowired
	private ProdutoRepository pr;

   

    @GetMapping()
	public ModelAndView listarProdutos() {
		List<Produto> produto = pr.findAll();
		ModelAndView mv = new ModelAndView("home/index");
		mv.addObject("produto", produto);
		return mv;
	}

 	@GetMapping("/{id}/cadastro-produto")
    public String formProduto(@PathVariable Long id, Produto produto) {
        return "cadastros/formProdutos";
    }

    @GetMapping("/{id}")
	public ModelAndView verProdutos(@PathVariable Long id, Produto produto) {
		Optional<Comerciante> opt = cr.findById(id);
		ModelAndView md = new ModelAndView();

		if (opt.isEmpty()) {
			md.setViewName("redirect:/produtos");
			return md;
		}

		Comerciante comerciante = opt.get();
		md.addObject("comerciante", comerciante);

		List<Produto> produtos = pr.findByComerciante(comerciante);
		md.addObject("produtos", produtos);
		md.setViewName("home/produtos");

		return md;
	}

	@PostMapping("/{idComerciante}/cadastro-produto")
	public String cadastrarProduto(@PathVariable Long idComerciante, Produto produto, BindingResult result,
			@RequestParam("file") MultipartFile arquivo, @RequestParam String filePath, Model model) {

		if (result.hasErrors()) {

			return formProduto(idComerciante, produto);
		}
		

		System.out.println("Id do comerciante:" + idComerciante);
		System.out.println(produto);

		Optional<Comerciante> opt = cr.findById(idComerciante);

		if (opt.isEmpty()) {
			return "redirect:/comerciantes";
		}
                Comerciante comerciante = opt.get();
				produto.setComerciante(comerciante);
				pr.save(produto);
		try {
			if (!arquivo.isEmpty() && arquivo != null) {
				String contentType = arquivo.getContentType();


				if (contentType != null && contentType.startsWith("image")) {

				

				byte[] bytes = arquivo.getBytes();
				String nomeOriginal = arquivo.getOriginalFilename(); // Obtenha o nome original do arquivo
				Path caminho = Paths.get(caminhoImagensProduto + String.valueOf(produto.getId()) + nomeOriginal); 
				
				Files.write(caminho, bytes);

				produto.setNomeImg(String.valueOf(produto.getId()) + nomeOriginal); 
				pr.save(produto);
				System.out.println("Caminho completo do arquivo: " + caminho);

				} else {
					model.addAttribute("erro", "Apenas arquivos de imagem são permitidos.");
					return "cadastros/formProdutos"; // substitua "sua-pagina" pelo nome da sua página Thymeleaf
				}
			} else if (filePath != null && !filePath.isEmpty()) {
				// Se não houver uma nova imagem e há um caminho existente, use o caminho
				// existente
				produto.setNomeImg(filePath);
			} else {
				// Se não houver uma nova imagem e nenhum caminho existente, defina como
				// "perfilNulo.png"
				produto.setNomeImg("imgPadrao.png");
			}

			pr.save(produto);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/produtos";
	}
    @GetMapping("/{idComerciante}/produtos/{idProduto}/edit-produto")
	public ModelAndView selecionarProduto(@PathVariable Long idComerciante, @PathVariable Long idProduto) {

		ModelAndView md = new ModelAndView();
		Optional<Comerciante> optComerciante = cr.findById(idComerciante);
		Optional<Produto> optProduto = pr.findById(idProduto);

		if (optComerciante.isEmpty() || optProduto.isEmpty()) {
			md.setViewName("redirect:/comerciantes");
			return md;
		}

		Comerciante comerciante = optComerciante.get();
		Produto produto = optProduto.get();

		if (comerciante.getId() != produto.getComerciante().getId()) {
			md.setViewName("redirect:/comerciantes");
			return md;
		}

		md.setViewName("cadastros/EditProduto");
		md.addObject("produto", produto);
		md.addObject("comerciante", comerciante);

		return md;
	}

    @GetMapping("/{idComerciante}/produtos/{idProduto}/apagar")
	public String apagarProduto(@PathVariable Long idComerciante, @PathVariable Long idProduto) {

		Optional<Comerciante> optComerciante = cr.findById(idComerciante);
		Optional<Produto> optProduto = pr.findById(idProduto);

		if (optComerciante.isPresent() && optProduto.isPresent()) {
			Comerciante comerciante = optComerciante.get();
			Produto produto = optProduto.get();

			if (comerciante.getId() == produto.getComerciante().getId()) {
				pr.delete(produto);
			}
		}

		return "redirect:/produtos/{idComerciante}";
	}
    
}
