package by.innowise.registrationapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDto {

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
    @NotNull
    @NotEmpty
    @Size(min = 10, max = 100)
    private String password;
    @Past
    @NotNull
    private LocalDate birthday;
}
