package by.innowise.registrationapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthday;
    private String role;
}
