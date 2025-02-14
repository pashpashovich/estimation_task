package by.innowise.registrationapp.controller;

import by.innowise.registrationapp.dto.UserDto;
import by.innowise.registrationapp.dto.UserUpdateDto;
import by.innowise.registrationapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        UserDto dto = userService.findById(id);
        model.addAttribute("user", dto);
        return "user/user-profile";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        UserUpdateDto userUpdateDto = userService.createUserUpdateDto(id);
        model.addAttribute("userUpdateDto", userUpdateDto);
        model.addAttribute("userId", id);
        return "user/user-edit";
    }

    @PostMapping("/{id}/edit")
    public String updateUserProfile(
            @PathVariable Long id,
            @Valid @ModelAttribute("userUpdateDto") UserUpdateDto userUpdateDto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("userId", id);
            return "user/user-edit";
        }
        userService.updateUser(id, userUpdateDto);
        return "redirect:/user/profile/" + id;
    }

}
