package projeto.shao.commerce.shaocommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersControllers {

    @GetMapping("/login")
    public String login(){
        return "login/login";
    }
    
}
