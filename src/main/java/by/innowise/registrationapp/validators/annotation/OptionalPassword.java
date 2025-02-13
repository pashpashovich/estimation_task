package by.innowise.registrationapp.validators.annotation;

import by.innowise.registrationapp.validators.OptionalPasswordSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OptionalPasswordSizeValidator.class)
public @interface OptionalPassword {
    String message() default "Пароль не соответствует требованиям сложности. Должно быть минимум 8 символов, а также минимум одна строчная и заглавная буквы";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
