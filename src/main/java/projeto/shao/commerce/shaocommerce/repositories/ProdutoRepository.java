package projeto.shao.commerce.shaocommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.shao.commerce.shaocommerce.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
