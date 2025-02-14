package by.innowise.registrationapp.exception_handler;

import by.innowise.registrationapp.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(NotFoundException ex, Model model) {
        log.error("Ошибка: {}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        return new ModelAndView("error/404");
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex, Model model) {
        log.error("Непредвиденная ошибка: ", ex);
        model.addAttribute("errorMessage", "Внутренняя ошибка сервера");
        return new ModelAndView("error/500");
    }
}
