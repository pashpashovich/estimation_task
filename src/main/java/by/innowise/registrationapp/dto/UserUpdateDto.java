package by.innowise.registrationapp.dto;

import by.innowise.registrationapp.validators.annotation.OptionalPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserUpdateDto {
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 25)
    private String firstName;
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String lastName;
    @NotNull
    @NotEmpty
    @Email
    private String email;
    @OptionalPassword
    private String password;
    @Past
    @NotNull
    private LocalDate birthday;
}
