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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import projeto.shao.commerce.shaocommerce.Enums.Perfil;
import projeto.shao.commerce.shaocommerce.Util.PasswordUtil;
import projeto.shao.commerce.shaocommerce.models.Comerciante;
import projeto.shao.commerce.shaocommerce.models.Produto;
import projeto.shao.commerce.shaocommerce.repositories.ComercianteRepository;
import projeto.shao.commerce.shaocommerce.repositories.ProdutoRepository;
import projeto.shao.commerce.shaocommerce.services.ComercianteUserDetailsImpl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Controller
@RequestMapping("/comerciantes")
public class ComerciantesControllers {
	// private static String caminhoImagens = "C:\\Users\\70204923476\\workspaces\\shaocommerce\\src\\main\\resources\\static\\upload\\";
	// private static String caminhoImagensProduto = "C:\\Users\\70204923476\\workspaces\\shaocommerce\\src\\main\\resources\\static\\uploadProduto\\";
	//private static String caminhoImagens = "D:/Usuario/Shao-commerce/src/main/resources/static/upload/";
    private static String caminhoImagens = "C:\\Users\\20201204010025\\Desktop\\ProjetoPI\\Shao-commerce\\src\\main\\resources\\static\\upload\\";
	public static String getCaminhoImagens() {
		return caminhoImagens;
	}

	public static void setCaminhoImagens(String caminhoImagens) {
		ComerciantesControllers.caminhoImagens = caminhoImagens;
	}



	//private static String caminhoImagensProduto = "D:/Usuario/Shao-commerce/src/main/resources/static/uploadProduto/";
    private static String caminhoImagensProduto = "C:\\Users\\20201204010025\\Desktop\\ProjetoPI\\Shao-commerce\\src\\main\\resources\\static\\uploadProduto\\";

	

	@Autowired
	private ComercianteRepository cr;

	@Autowired
	private ProdutoRepository pr;

	@GetMapping("/formComerciante")
	public ModelAndView cadastro(Comerciante comerciante) {
		 ModelAndView mv = new ModelAndView("cadastros/formComerciante");
        mv.addObject("comerciante", comerciante);
        Perfil[] profiles = {Perfil.COMERCIANTE};
        mv.addObject("perfils", profiles);
		

        return mv;
		
	}

@PostMapping
public ModelAndView salvarComerciante(@Valid Comerciante comerciante, BindingResult result,
        @RequestParam("file") MultipartFile arquivo, @RequestParam String filePath, Model model, RedirectAttributes attributes) {
    System.out.println("Caminho do arquivo: " + caminhoImagens);
	String hashSenha = PasswordUtil.encoder(comerciante.getSenha());
        comerciante.setSenha(hashSenha);
    ModelAndView mv = new ModelAndView("cadastros/formComerciante");
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
                Path caminho = Paths.get(caminhoImagens).toAbsolutePath().resolve(String.valueOf(comerciante.getId()) + nomeOriginal);

                Files.write(caminho, bytes);

                System.out.println(caminho);

                comerciante.setNomeImg(String.valueOf(comerciante.getId()) + nomeOriginal);
            } else {
                model.addAttribute("erro", "Apenas arquivos de imagem são permitidos.");
                return mv;
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
        attributes.addFlashAttribute("mensagem", "Comerciante salvo com sucesso!");
        System.out.println("Comerciante Salvo");
    } catch (IOException e) {
        e.printStackTrace();
    }
  
    mv.setViewName("home/vendedores");
    return mv;
}
	@GetMapping("/comerciantes")
	public ModelAndView listar() {
		List<Comerciante> comerciantes = cr.findAll();
		ModelAndView mv = new ModelAndView("home/vendedores");
		mv.addObject("comerciantes", comerciantes);

		return mv;
	}
	

	

	@GetMapping("/{id}/remover")
	public String apagarComerciante(@PathVariable Long id, RedirectAttributes attributes) {

		Optional<Comerciante> opt = cr.findById(id);

		if (opt.isPresent()) {
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
            attributes.addFlashAttribute("mensagem", "Comerciante deletado salvo com sucesso!");
		}
        
		return "redirect:/comerciantes";
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
		md.setViewName("cadastros/editComerciante");
		md.addObject("comerciante", comerciante);
		Perfil[] profiles = {Perfil.COMERCIANTE};
        md.addObject("perfils", profiles);
		md.addObject("senha", comerciante.getSenha());

		return md;
	}

	@GetMapping("/editar-perfil")
	public ModelAndView EditaComerciante(@RequestParam("id") Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Comerciante> opt = cr.findById(id);

        
     // Obtém o Authentication do contexto de segurança
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // Verifica se o usuário autenticado possui a permissão adequada
    if (!id.equals(((ComercianteUserDetailsImpl) authentication.getPrincipal()).getId())) {
        // Redireciona para uma página de acesso negado ou para a página inicial
        md.setViewName("redirect:/produtos"); // Substitua pelo caminho desejado
        return md;
    }
		if (opt.isEmpty()) {
			md.setViewName("redirect:/comerciantes");
			return md;
		}
		Comerciante comerciante = opt.get();
		md.setViewName("cadastros/editComerciante");
		md.addObject("comerciante", comerciante);
		Perfil[] profiles = {Perfil.COMERCIANTE};
        md.addObject("perfils", profiles);
		md.addObject("senha", comerciante.getSenha());

		return md;
	}

	@PostMapping("/editar-perfil")
	public ModelAndView salvarEdicaoComerciante(@Valid Comerciante comerciante, BindingResult result,
        @RequestParam("file") MultipartFile arquivo, @RequestParam String filePath, Model model, RedirectAttributes attributes) {
    System.out.println("Caminho do arquivo: " + caminhoImagens);
    ModelAndView mv = new ModelAndView("cadastros/formComerciante");
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
                Path caminho = Paths.get(caminhoImagens).toAbsolutePath().resolve(String.valueOf(comerciante.getId()) + nomeOriginal);

                Files.write(caminho, bytes);

                System.out.println(caminho);

                comerciante.setNomeImg(String.valueOf(comerciante.getId()) + nomeOriginal);
            } else {
                model.addAttribute("erro", "Apenas arquivos de imagem são permitidos.");
                return mv;
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
        attributes.addFlashAttribute("mensagem", "Atualização salvo com sucesso!");
        System.out.println("Comerciante Salvo");
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    mv.setViewName("redirect:/produtos");
    return mv;

}

}