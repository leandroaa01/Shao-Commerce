package projeto.shao.commerce.shaocommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.shao.commerce.shaocommerce.models.Comerciante;


public interface ComercianteRepository extends JpaRepository<Comerciante, Long>{
    Optional<Comerciante>  findByEmail(String email);
   

}
