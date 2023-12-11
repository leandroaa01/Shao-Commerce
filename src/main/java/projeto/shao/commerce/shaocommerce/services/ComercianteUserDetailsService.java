package projeto.shao.commerce.shaocommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import projeto.shao.commerce.shaocommerce.models.Comerciante;
import projeto.shao.commerce.shaocommerce.repositories.ComercianteRepository;

@Service
public class ComercianteUserDetailsService implements UserDetailsService {
    @Autowired
    private ComercianteRepository cr;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Comerciante comerciante = cr.findByEmail(email)
        .orElseThrow( () -> new UsernameNotFoundException("Usuário não foi encontrado na base de dados"));
        return new ComercianteUserDetailsImpl(comerciante);
    }

    
}