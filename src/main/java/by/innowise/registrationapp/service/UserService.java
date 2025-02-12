package by.innowise.registrationapp.service;

import by.innowise.registrationapp.dao.UserDao;
import by.innowise.registrationapp.dto.UserCreateDto;
import by.innowise.registrationapp.dto.UserDto;
import by.innowise.registrationapp.entity.User;
import by.innowise.registrationapp.enums.Role;
import by.innowise.registrationapp.exceeption.UserNotFoundException;
import by.innowise.registrationapp.mapper.UserMapper;
import by.innowise.registrationapp.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;

//    public UserDto findById(Long id) {
//        return userMapper.toDto(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found")));
//    }
//
//    public UserDto authenticate(String username, String password) {
//        User user = userRepository.findByEmail(username).orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
//
//        if (Boolean.TRUE.equals(user.getIsBlocked())) {
//            log.warn("Access denied: " + username);
//            throw new NoAccessException("User is blocked");
//        }
//
//        if (PasswordUtils.verify(password, user.getPassword())) {
//            log.info("Login successful: " + username);
//            return userMapper.toDto(user);
//        } else {
//            log.warn("Incorrect login or password for user: " + username);
//            throw new BadCredentialsException("Неверный логин или пароль");
//        }
//    }

    public UserDto register(UserCreateDto userCreateDto) {
        if (userDao.findByEmail(userCreateDto.getEmail()).isPresent()) {
            throw new UserNotFoundException("Email already exists");
        }

        User userEntity = userMapper.toEntityCreate(userCreateDto)
                .setRole(Role.USER)
                .setPassword(PasswordUtils.hash(userCreateDto.getPassword()));

        User savedUser = userDao.save(userEntity);
        log.info("New user registered with email: " + userEntity.getEmail());
        return userMapper.toDto(savedUser);
    }
}

