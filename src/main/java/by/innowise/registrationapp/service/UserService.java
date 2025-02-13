package by.innowise.registrationapp.service;

import by.innowise.registrationapp.dao.UserDao;
import by.innowise.registrationapp.dto.UserCreateDto;
import by.innowise.registrationapp.dto.UserDto;
import by.innowise.registrationapp.entity.User;
import by.innowise.registrationapp.enums.Role;
import by.innowise.registrationapp.exception.EmailAlreadyExistsException;
import by.innowise.registrationapp.exception.NotFoundException;
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


    public UserDto findById(Long id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с ID %s не найден", id)));
        return userMapper.toDto(user);
    }

    public void register(UserCreateDto userCreateDto) {
        if (userDao.findByEmail(userCreateDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Пользователь с таким email уже зарегистрирован");
        }
        User userEntity = userMapper.toEntityCreate(userCreateDto)
                .setRole(Role.USER)
                .setPassword(PasswordUtils.hash(userCreateDto.getPassword()));

        userDao.save(userEntity);
        log.info("New user registered with email: " + userEntity.getEmail());
    }
}

