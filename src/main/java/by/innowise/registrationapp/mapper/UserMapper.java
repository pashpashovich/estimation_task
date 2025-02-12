package by.innowise.registrationapp.mapper;

import by.innowise.registrationapp.dto.UserCreateDto;
import by.innowise.registrationapp.dto.UserDto;
import by.innowise.registrationapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntityCreate(UserCreateDto userDto);
    UserDto toDto(User user);
}


