package by.innowise.registrationapp.service;

import by.innowise.registrationapp.dao.UserDao;
import by.innowise.registrationapp.entity.User;
import by.innowise.registrationapp.security.UserDetailsI;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return new UserDetailsI(user.getId(), user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().toString())));
    }
}
