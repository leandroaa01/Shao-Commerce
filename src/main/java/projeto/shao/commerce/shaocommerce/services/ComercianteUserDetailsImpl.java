package projeto.shao.commerce.shaocommerce.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import projeto.shao.commerce.shaocommerce.Enums.Perfil;
import projeto.shao.commerce.shaocommerce.models.Comerciante;

public class ComercianteUserDetailsImpl implements UserDetails  {

    private Comerciante comerciante;

    public ComercianteUserDetailsImpl(Comerciante comerciante){
        this.comerciante = comerciante;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Perfil perfil = comerciante.getPerfil();
        if(perfil == Perfil.ADMIN){
            perfil = Perfil.ADMIN;
        }else{
            perfil = Perfil.COMERCIANTE;
        }
        return AuthorityUtils.createAuthorityList(perfil.toString());
    }
    public String getNome(){
        return comerciante.getNome(); 
       }
       public Long getId(){
        return comerciante.getId();
       }
       public String displayImagem(){
        return comerciante.getNomeImg();
       }
    @Override
    public String getPassword() {    
        return  comerciante.getSenha(); 
     }

    @Override
    public String getUsername() {
       return comerciante.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
         return true;
    }
    
}
