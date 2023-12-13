package projeto.shao.commerce.shaocommerce.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import projeto.shao.commerce.shaocommerce.Enums.Perfil;
import projeto.shao.commerce.shaocommerce.models.Cliente;


public class ClienteUserDetailsImpl implements UserDetails  {

    private Cliente cliente;

    public ClienteUserDetailsImpl( Cliente cliente){
        this.cliente = cliente;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Perfil perfil = cliente.getPerfil();
        if(perfil == Perfil.ADMIN){
            perfil = Perfil.ADMIN;
        }else{
            perfil = Perfil.CLIENTE;
        }
        return AuthorityUtils.createAuthorityList(perfil.toString());
    }
    public String getNome(){
        return cliente.getNome(); 
       }
       public Long getId(){
        return cliente.getId();
       }
       public Perfil getPerfil(){
        return cliente.getPerfil();
       }
    @Override
    public String getPassword() {    
        return  cliente.getSenha(); 
     }

    @Override
    public String getUsername() {
       return cliente.getEmail();
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
