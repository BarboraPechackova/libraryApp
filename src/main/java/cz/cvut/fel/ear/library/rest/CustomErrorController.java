package cz.cvut.fel.ear.library.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "redirect:/public/books.xhtml"; // Redirect to your default page
    }

    public String getErrorPath() {
        return "/error";
    }
}
