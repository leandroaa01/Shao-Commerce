package projeto.shao.commerce.shaocommerce.controllers;

import java.io.File;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import projeto.shao.commerce.shaocommerce.models.Comerciante;
import projeto.shao.commerce.shaocommerce.models.Produto;
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
		cr.saveAndFlush(comerciante);

		try {
			if (!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				Path caminho = Paths
						.get(caminhoImagens + String.valueOf(comerciante.getId()) + arquivo.getOriginalFilename());
				Files.write(caminho, bytes);

				comerciante.setNomeImg(String.valueOf(comerciante.getId()) + arquivo.getOriginalFilename());
				cr.saveAndFlush(comerciante);
				System.out.println("Caminho completo do arquivo: " + caminho);
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
	@GetMapping("/comerciantes/mostrarImagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException{
		File imagemArquivo = new File(caminhoImagens+imagem);

	if(imagem!=null || imagem.trim().length()> 0){
 
		return Files.readAllBytes(imagemArquivo.toPath());
		
	}
		return null;
	}
		@PostMapping("/{idComerciante}")
		public String cadastrarProduto(@PathVariable Long idComerciante, Produto produto){
			System.out.println("Id do comerciante:" +idComerciante);
			
			Optional<Comerciante> opt = cr.findById(idComerciante);
			if(opt.isEmpty()){
				return "redirect:/comerciantes";
			}
			Comerciante comerciante = opt.get();

			produto.setComerciante(comerciante);

			
			return "redirect:/comerciante/" +idComerciante;

		}

}
