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

import jakarta.validation.Valid;
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
	public String cadastro(Comerciante comerciante) {
		return "cadastros/form";
	}

	@PostMapping
public String salvarComerciante(@Valid Comerciante comerciante, BindingResult result,
        @RequestParam("file") MultipartFile arquivo, @RequestParam("filePath") String filePath) {

				if (result.hasErrors()) {
					return cadastro(comerciante);
				}

				cr.save(comerciante);

				try {
					if (!arquivo.isEmpty() && arquivo != null) {
						// Processar e salvar a nova imagem
						byte[] bytes = arquivo.getBytes();
						String nomeOriginal = arquivo.getOriginalFilename();
						Path caminho = Paths.get(caminhoImagens + String.valueOf(comerciante.getId()) + nomeOriginal);
						Files.write(caminho, bytes);
			
						comerciante.setNomeImg(String.valueOf(comerciante.getId()) + nomeOriginal);
					} else if (filePath != null && !filePath.isEmpty()) {
						// Se não houver uma nova imagem e há um caminho existente, use o caminho existente
						comerciante.setNomeImg(filePath);
					} else {
						// Se não houver uma nova imagem e nenhum caminho existente, defina como "perfilNulo.png"
						comerciante.setNomeImg("perfilNulo.png");
					}
			
					cr.save(comerciante);
					System.out.println("Comerciante Salvo");
				} catch (IOException e) {
					e.printStackTrace();
				}
			
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
	public ModelAndView verProdutos(@PathVariable Long id, Produto produto) {
		Optional<Comerciante> opt = cr.findById(id);
		ModelAndView md = new ModelAndView();

		if (opt.isEmpty()) {
			md.setViewName("redirect:/comerciantes");
			return md;
		}

		Comerciante comerciante = opt.get();
		md.addObject("comerciante", comerciante);

		List<Produto> produtos = pr.findByComerciante(comerciante);
		md.addObject("produtos", produtos);
		md.setViewName("/produtos");

		return md;
	}

	@PostMapping("/{idComerciante}/produtos")
	public String cadastrarProduto(@PathVariable Long idComerciante, Produto produto, BindingResult result,
			@RequestParam("file") MultipartFile arquivo,@RequestParam("filePath") String filePath) {

				if (result.hasErrors()) {
					
					return "redirect:/cadastros/formProdutos";
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
							byte[] bytes = arquivo.getBytes();
							String nomeOriginal = arquivo.getOriginalFilename(); // Obtenha o nome original do arquivo
							Path caminho = Paths.get(caminhoImagensProduto +String.valueOf(produto.getId()) + nomeOriginal); // Use o nome original do arquivo
							Files.write(caminho, bytes);
			
							produto.setNomeImg(String.valueOf(produto.getId()) + nomeOriginal); // Define o nome da imagem como o nome original
							pr.save(produto);
							System.out.println("Caminho completo do arquivo: " + caminho);
						}else if (filePath != null && !filePath.isEmpty()) {
            				// Se não houver uma nova imagem e há um caminho existente, use o caminho existente
           					 produto.setNomeImg(filePath);
        				} else {
            				// Se não houver uma nova imagem e nenhum caminho existente, defina como "imgPadrao.png"
							produto.setNomeImg("imgPadrao.png");
							
						
					}pr.save(produto);
					System.out.println("Produto Salvo");
				} catch (IOException e) {
						e.printStackTrace();
					}
				return "redirect:/comerciantes/{idComerciante}";
	}

	@GetMapping("/{id}/remover")
	public String apagarComerciante(@PathVariable Long id) {

		Optional<Comerciante> opt = cr.findById(id);

		if (!opt.isEmpty()) {
			Comerciante comerciante = opt.get();

			List<Produto> produtos = pr.findByComerciante(comerciante);

			for (Produto produto : produtos) {
				String nomeImagem = produto.getNomeImg();
				if (nomeImagem != null && !nomeImagem.equals("imgPadrao.png")) {
					try {
						Files.deleteIfExists(Paths.get(caminhoImagensProduto + nomeImagem));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				pr.deleteAll(produtos);
			}
			cr.delete(comerciante);
		}
		return "redirect:/comerciantes";
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

		return "redirect:/comerciantes/{idComerciante}";
	}

	@GetMapping("/{id}/selecionar")
	public ModelAndView selecionarComerciante(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Comerciante> opt = cr.findById(id);
		if (opt.isEmpty()) {
			md.setViewName("redirect:/comerciantes");
			return md;
		}
		Comerciante comerciante = opt.get();
		md.setViewName("cadastros/form");
		md.addObject("comerciante", comerciante);

		return md;
	}

	@GetMapping("/{idComerciante}/produtos/{idProduto}/selecionar")
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

		md.setViewName("cadastros/formProdutoEdit");
		md.addObject("produto", produto);
		md.addObject("comerciante", comerciante);

		return md;
	}

}
