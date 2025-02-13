package by.innowise.registrationapp.controller;

import by.innowise.registrationapp.dto.UserCreateDto;
import by.innowise.registrationapp.exception.EmailAlreadyExistsException;
import by.innowise.registrationapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reg")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping()
    public String showRegistrationForm(Model model) {
        model.addAttribute("userCreateDto", new UserCreateDto());
        return "register";
    }

    @PostMapping()
    public String registerUser(@Valid @ModelAttribute("userCreateDto") UserCreateDto userCreateDto,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.register(userCreateDto);
        } catch (EmailAlreadyExistsException e) {
            result.rejectValue("email", "error.email", e.getMessage());
            return "register";
        }
        return "redirect:/";
    }
}
