package projeto.shao.commerce.shaocommerce.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import projeto.shao.commerce.shaocommerce.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

     Cliente findByEmail(String email);
    
}
