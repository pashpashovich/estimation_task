package by.innowise.registrationapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorController {

    @GetMapping("/error/access-denied")
    public String accessDeniedPage(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("errorMessage", message != null ? message : "У вас нет доступа к этой странице.");
        return "error/access-denied";
    }
}
