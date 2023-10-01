package com.example.practice.controller;
import com.example.practice.controller.Mapper.UserMapper;
import com.example.practice.controller.userdto.UserDto;
import com.example.practice.entity.DateRange;
import com.example.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test_app")
@Validated
public class UserController {
    private final UserService userService;
    @Value("${user.minAge}")
    private int minAge;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return new ResponseEntity<>(
                userService.getAll().stream()
                        .map(userMapper::userToDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

//    Create user. It allows to register users who are more than [18] years old. The value [18] should be taken from properties file.
    @PostMapping(value = "/{day}/{month}/{year}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto, @PathVariable int day, @PathVariable int month, @PathVariable int year) {
        LocalDate localDate1 = LocalDate.now();
//        Add validation against email pattern
        if (!userDto.getEmail().matches("(.*)@gmail.com(.*)") && !userDto.getEmail().matches("(.*)@yahoo.com(.*)") && !userDto.getEmail().matches("(.*)@nure.ua(.*)")) {
            return ResponseEntity.badRequest().body(null);

        }
//        Value must be earlier than current date
        if ((localDate1.getYear() - year) < minAge){
            return ResponseEntity.badRequest().body(null);
        }
        LocalDate dateOfBirth = LocalDate.of(year, month, day);
        userDto.setDateOfBirth(dateOfBirth);
            return new ResponseEntity<>(
                    userMapper.userToDto(
                            userService.saveUser(
                                    userMapper.toEntity(userDto))),
                    HttpStatus.CREATED
            );
        }

//   Update all user fields
    @PutMapping
    public UserDto update(@RequestBody UserDto userDto){
        return userMapper.userToDto(userService.update(userMapper.toEntity(userDto)));
    }

//    Delete user
    @DeleteMapping
    public Long delete(@RequestParam Long id){
        return userService.delete(id);
    }

//    Update one/some user fields
    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userUpdate) {
        return userMapper.userToDto(userService.updateUser(id, userMapper.toEntity(userUpdate)));
    }

//    Search for users by birth date range. Add the validation which checks that “From” is less than “To”.  Should return a list of objects
    @GetMapping ("/findByDateRange")
    public ResponseEntity<List<UserDto>> findByDate(@Valid @RequestBody DateRange dateRange){
        return new ResponseEntity<>(userService
                .findUserByDateOfBirth(dateRange).stream()
                .map(userMapper::userToDto).collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
