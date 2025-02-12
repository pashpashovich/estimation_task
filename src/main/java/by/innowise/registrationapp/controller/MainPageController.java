package by.innowise.registrationapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/success")
@RequiredArgsConstructor
public class MainPageController {
    @GetMapping()
    public String showSuccess() {
        return "success";
    }
}
