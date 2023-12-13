package projeto.shao.commerce.shaocommerce.controllers;



import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String customError(HttpServletRequest request){

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = Integer.parseInt(status.toString());

          if(statusCode == HttpStatus.NOT_FOUND.value()){
           return "redirect:/produtos";
        }  else if (statusCode == 999 ) {
            return "redirect:/produto";
        }

       

        return "redirect:/produtos";

    }
    
}
