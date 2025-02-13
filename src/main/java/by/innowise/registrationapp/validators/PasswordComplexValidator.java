package by.innowise.registrationapp.validators;

import by.innowise.registrationapp.validators.annotation.ComplexPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordComplexValidator implements ConstraintValidator<ComplexPassword, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{10,}$";
        return value.matches(passwordPattern);
    }
}

