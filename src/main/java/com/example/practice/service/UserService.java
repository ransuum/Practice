package com.example.practice.service;

import com.example.practice.entity.DateRange;
import com.example.practice.repository.UserRepos;
import com.example.practice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final UserRepos userRepos;
    @Autowired
    public UserService(UserRepos userRepos) {
        this.userRepos = userRepos;
    }

    public List<User> getAll(){
        return userRepos.findAll();
    }
    public User saveUser(User user){
        return userRepos.save(user);
    }
    public Long delete(Long id){
        userRepos.deleteById(id);
        return id;
    }
    public User update(User user){
        return userRepos.save(user);
    }

    public User updateUser(Long id, User updateUser){
        User existingUser = userRepos.findById(id).orElse(null);
        if (existingUser != null) {
            if (updateUser.getEmail() != null) {
                existingUser.setEmail(updateUser.getEmail());
            }
            if (updateUser.getAddress() != null) {
                existingUser.setAddress(updateUser.getAddress());
            }
            if (updateUser.getPhone() != null) {
                existingUser.setPhone(updateUser.getPhone());
            }
            if (updateUser.getName() != null){
                existingUser.setName(updateUser.getName());
            }
            if (updateUser.getSurname() != null){
                existingUser.setSurname(updateUser.getSurname());
            } if (updateUser.getDateOfBirth() != null){
                existingUser.setDateOfBirth(LocalDate.parse(updateUser.getDateOfBirth().toString()));
            }
            userRepos.save(existingUser);
        }
        return existingUser;
    }
    public List<User> findUserByDateOfBirth(DateRange dateRange){
        LocalDate fromDate = dateRange.getFromDate();
        LocalDate toDate = dateRange.getToDate();

        return userRepos.findByDateOfBirthBetween(fromDate, toDate);
    }
}
