package com.example.practice.controller.Mapper;

import com.example.practice.controller.userdto.UserDto;
import com.example.practice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserMapper {
    public UserDto userToDto(User user) {
        LocalDate dateOfBirth = LocalDate.parse(user.getDateOfBirth().toString());
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                dateOfBirth,
                user.getAddress(),
                user.getPhone()
        );
    }

    public User toEntity(UserDto userDto) {
        LocalDate dateOfBirth = null;
        if (userDto.getDateOfBirth() != null) {
            dateOfBirth = LocalDate.parse(userDto.getDateOfBirth().toString());
        }
        return new User(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getName(),
                userDto.getSurname(),
                dateOfBirth,
                userDto.getAddress(),
                userDto.getPhone()
        );
    }
}

