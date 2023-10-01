package com.example.practice.userServiceTest;

import com.example.practice.entity.DateRange;
import com.example.practice.entity.User;
import com.example.practice.repository.UserRepos;
import com.example.practice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepos userRepos;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        when(userRepos.findAll()).thenReturn(Arrays.asList(new User(), new User()));

        List<User> users = userService.getAll();
        assertEquals(2, users.size());
    }
    @Test
    public void testDeleteUser() {
        Long Id = 1L;

        when(userRepos.findById(Id)).thenReturn(Optional.of(new User()));

        Long deletedUserId = userService.delete(Id);

        verify(userRepos, times(1)).deleteById(Id);

        assertEquals(Id, deletedUserId);
    }
    @Test
    public void testSave(){
        User user = new User();
        when(userRepos.save(user)).thenReturn(user);
        User saveUser = userRepos.save(user);
        verify(userRepos, times(1)).save(user);
        assertEquals(user, saveUser);
    }
    @Test
    public void testUpdateAll(){
        User user = new User();
        when(userRepos.save(user)).thenReturn(user);
        User updateUser = userRepos.save(user);
        verify(userRepos, times(1)).save(user);
        assertEquals(user, updateUser);
    }
    @Test
    public void testUpdateOneOrMore(){
        User existingUser = new User();
        Long id = 1L;
        existingUser.setId(id);

        User updateUser = new User();
        updateUser.setEmail("newemail@example.com");
        updateUser.setAddress("Test");
        updateUser.setPhone("1234567890");
        updateUser.setName("Test");
        updateUser.setSurname("Test");
        updateUser.setDateOfBirth(LocalDate.now());

        when(userRepos.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepos.save(existingUser)).thenReturn(existingUser);

        User updatedUser = userService.updateUser(id, updateUser);

        verify(userRepos, times(1)).findById(id);

        assertEquals(updateUser.getEmail(), existingUser.getEmail());
        assertEquals(updateUser.getAddress(), existingUser.getAddress());
        assertEquals(updateUser.getPhone(), existingUser.getPhone());
        assertEquals(updateUser.getName(), existingUser.getName());
        assertEquals(updateUser.getSurname(), existingUser.getSurname());
        assertEquals(updateUser.getDateOfBirth(), existingUser.getDateOfBirth());

        verify(userRepos, times(1)).save(existingUser);

        assertEquals(existingUser, updatedUser);
    }
    @Test
    public void testFindUserByDateOfBirth(){
        LocalDate fromDate = LocalDate.of(2000, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 12, 31);
        DateRange dateRange = new DateRange(fromDate, toDate);

        User user1 = new User();
        User user2 = new User();

        when(userRepos.findByDateOfBirthBetween(fromDate, toDate)).thenReturn(Arrays.asList(user1, user2));

        List<User> usersInRange = userService.findUserByDateOfBirth(dateRange);

        verify(userRepos, times(1)).findByDateOfBirthBetween(fromDate, toDate);

        assertEquals(2, usersInRange.size());
        assertTrue(usersInRange.contains(user1));
        assertTrue(usersInRange.contains(user2));
    }
}


