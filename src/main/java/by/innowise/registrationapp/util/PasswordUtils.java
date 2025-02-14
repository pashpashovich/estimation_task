package by.innowise.registrationapp.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCrypt;

@UtilityClass
public class PasswordUtils {
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
