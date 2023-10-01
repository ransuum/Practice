package com.example.practice.userMapperTest;

import com.example.practice.controller.Mapper.UserMapper;
import com.example.practice.controller.userdto.UserDto;
import com.example.practice.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTest {
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    public void testUserToDto() {

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("Test");
        user.setSurname("Test");
        user.setDateOfBirth(LocalDate.of(2009, 1, 1));
        user.setAddress("Test");
        user.setPhone("Test");

        UserDto userDto = userMapper.userToDto(user);

        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
    }
}
