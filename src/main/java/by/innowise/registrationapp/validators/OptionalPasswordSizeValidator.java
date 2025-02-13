package by.innowise.registrationapp.validators;

import by.innowise.registrationapp.validators.annotation.OptionalPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OptionalPasswordSizeValidator implements ConstraintValidator<OptionalPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{10,}$";
        return value.matches(passwordPattern);
    }
}

