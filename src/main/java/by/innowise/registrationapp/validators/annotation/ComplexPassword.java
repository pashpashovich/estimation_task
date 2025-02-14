package by.innowise.registrationapp.validators.annotation;

import by.innowise.registrationapp.validators.PasswordComplexValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PasswordComplexValidator.class)
public @interface ComplexPassword {
    String message() default "Пароль не соответствует требованиям сложности. Должно быть минимум 8 символов, а также минимум одна строчная и заглавная латинские буквы";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
