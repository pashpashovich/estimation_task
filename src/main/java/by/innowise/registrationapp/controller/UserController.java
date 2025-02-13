package by.innowise.registrationapp.controller;

import by.innowise.registrationapp.dto.UserDto;
import by.innowise.registrationapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/profile/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        UserDto dto = userService.findById(id);
        model.addAttribute("user", dto);
        return "user-profile";
    }

}
