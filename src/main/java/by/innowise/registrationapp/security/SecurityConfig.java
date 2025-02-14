package by.innowise.registrationapp.security;

import by.innowise.registrationapp.dao.UserDao;
import by.innowise.registrationapp.exception_handler.CustomAccessDeniedHandler;
import by.innowise.registrationapp.service.UserInfoUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDao userDao;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/reg").permitAll()
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/profile/{userId}", "/user/profile/{userId}/edit")
                        .access((authenticationSupplier, context) -> hasPermissionToProfile(context.getVariables().get("userId"), authenticationSupplier))
                        .requestMatchers(HttpMethod.POST, "/user/profile/{userId}/edit")
                        .access((authenticationSupplier, context) -> hasPermissionToProfile(context.getVariables().get("userId"), authenticationSupplier))
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .successHandler(myAuthenticationSuccessHandler())
                        .failureHandler((request, response, exception) ->
                                response.sendRedirect("/?error=" + URLEncoder.encode("Неверный логин или пароль", StandardCharsets.UTF_8)))
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            HttpSession session = request.getSession(false);
                            if (session != null) {
                                session.invalidate();
                            }
                            response.sendRedirect("/");
                        })
                        .permitAll())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(accessDeniedHandler()))
                .authenticationProvider(authenticationProvider());

        return http.build();
    }

    private AuthorizationDecision hasPermissionToProfile(Object userIdObj, Supplier<Authentication> authenticationSupplier) {
        Authentication authentication = authenticationSupplier.get();

        if (userIdObj == null || authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Доступ запрещен: пользователь не аутентифицирован");
        }

        Long userId = Long.parseLong(userIdObj.toString());

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetailsI userDetails)) {
            throw new AccessDeniedException("Доступ запрещен: некорректный объект аутентификации");
        }

        if (!userDetails.getId().equals(userId)) {
            throw new AccessDeniedException("Доступ запрещен: попытка доступа к чужому профилю");
        }

        return new AuthorizationDecision(true);
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MyAuthenticationSuccessHandler();
    }

    public static class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response,
                                            Authentication authentication) throws IOException {
            var userDetails = (UserDetailsI) authentication.getPrincipal();
            Long userId = userDetails.getId();
            String redirectUrl = "/user/profile/" + userId;
            response.sendRedirect(redirectUrl);
        }
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService(userDao);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
