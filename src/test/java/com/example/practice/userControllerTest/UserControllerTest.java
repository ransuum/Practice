package com.example.practice.userControllerTest;

import com.example.practice.controller.Mapper.UserMapper;
import com.example.practice.controller.UserController;
import com.example.practice.controller.userdto.UserDto;
import com.example.practice.entity.DateRange;
import com.example.practice.entity.User;
import com.example.practice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private UserController userController;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService, userMapper)).build();

        // Настройка моков
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(new User());
        when(userService.saveUser(any(User.class))).thenReturn(new User());
        when(userMapper.userToDto(any(User.class))).thenReturn(new UserDto());

    }

    @Test
    public void testGetAll() throws Exception {
        User user1 = new User(1L, "user1@example.com", "Test", "Test", null, "Test", "1234567890");
        User user2 = new User(2L, "user2@example.com", "Test2", "Test2", null, "Test2", "9876543210");

        UserDto userDto1 = new UserDto(1L, "user1@example.com", "Test", "Test", null, "Test", "1234567890");
        UserDto userDto2 = new UserDto(2L, "user2@example.com", "Test2", "Test2", null, "Test2", "9876543210");

        when(userService.getAll()).thenReturn(Arrays.asList(user1, user2));
        when(userMapper.userToDto(user1)).thenReturn(userDto1);
        when(userMapper.userToDto(user2)).thenReturn(userDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/test_app"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("user1@example.com"))
                .andExpect(jsonPath("$[0].name").value("Test"))
                .andExpect(jsonPath("$[0].surname").value("Test"))
                .andExpect(jsonPath("$[0].address").value("Test"))
                .andExpect(jsonPath("$[0].phone").value("1234567890"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].email").value("user2@example.com"))
                .andExpect(jsonPath("$[1].name").value("Test2"))
                .andExpect(jsonPath("$[1].surname").value("Test2"))
                .andExpect(jsonPath("$[1].address").value("Test2"))
                .andExpect(jsonPath("$[1].phone").value("9876543210"));

        verify(userService, times(1)).getAll();
    }
    @Test
    public void testFindByDate() throws Exception {
        LocalDate fromDate = LocalDate.of(1999, 1, 1);
        LocalDate toDate = LocalDate.of(2005, 12, 31);
        DateRange dateRange = new DateRange(fromDate, toDate);

        List<User> users = Arrays.asList(new User(), new User());

        when(userService.findUserByDateOfBirth(dateRange)).thenReturn(users);

        mockMvc.perform(post("/findByDate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromDate\": \"2000-01-01\", \"toDate\": \"2003-12-31\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(users.size()))
                .andReturn();
    }

}
