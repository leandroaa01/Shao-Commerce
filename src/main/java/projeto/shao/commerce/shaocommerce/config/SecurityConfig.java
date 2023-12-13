package projeto.shao.commerce.shaocommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import projeto.shao.commerce.shaocommerce.services.ComercianteUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {  
    @Autowired
    private ComercianteUserDetailsService cs;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http)throws Exception{

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
            .rememberMe();
            
            http.logout()
        .logoutRequestMatcher(
            new AntPathRequestMatcher("/logout", "GET")
        )
        .logoutSuccessUrl("/login");

        http.rememberMe()
        .key("keyRemember-me");
        

        return http.build();

        }
     
        @Autowired
         public void configureBlobal(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(cs).passwordEncoder(passwordEncoder());
        
    }
}
