package projeto.shao.commerce.shaocommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.shao.commerce.shaocommerce.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

     Optional<Cliente> findByEmail(String email);
    
}
