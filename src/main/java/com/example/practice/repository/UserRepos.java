package com.example.practice.repository;

import com.example.practice.controller.userdto.UserDto;
import com.example.practice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserRepos extends JpaRepository<User, Long> {
    List<User> findByDateOfBirthBetween(LocalDate fromDate, LocalDate toDate);
}
