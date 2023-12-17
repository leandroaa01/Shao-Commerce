package projeto.shao.commerce.shaocommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import projeto.shao.commerce.shaocommerce.services.ClienteUserDetailsService;
import projeto.shao.commerce.shaocommerce.services.ComercianteUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {  
    @Autowired
    private ComercianteUserDetailsService cs;

    @Autowired
    private ClienteUserDetailsService cl;

   
     @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http)throws Exception{

        http
            .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/login/conta").permitAll()
                .requestMatchers("/login/formComerciante").permitAll()
                .requestMatchers("/login/formUser").permitAll()
                .requestMatchers("/produtos/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/login/cadastro-user").permitAll()
                .requestMatchers(HttpMethod.POST,"/login/cadastro-comerciante").permitAll()
                .requestMatchers("/img/**").permitAll()
                 .requestMatchers("/upload/**").permitAll()
                  .requestMatchers("/uploadProduto/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .loginProcessingUrl("/login")
                .failureForwardUrl("/login")
                .defaultSuccessUrl("/produtos")
                .permitAll()
                .and()
            
            
            .logout()
        .logoutRequestMatcher(
            new AntPathRequestMatcher("/logout", "GET")
        )
        .logoutSuccessUrl("/login");
        

        

        return http.build();

        }
     
         @Autowired
    public void configureBlobal(AuthenticationManagerBuilder auth) throws Exception {
       auth
       .userDetailsService(cs).
       passwordEncoder(new BCryptPasswordEncoder());
       auth.
       userDetailsService(cl)
       .passwordEncoder(new BCryptPasswordEncoder());
        
    } 
    @Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
