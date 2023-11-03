package projeto.shao.commerce.shaocommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.shao.commerce.shaocommerce.models.Comerciante;
import projeto.shao.commerce.shaocommerce.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByComerciante(Comerciante comerciante);
    
}
