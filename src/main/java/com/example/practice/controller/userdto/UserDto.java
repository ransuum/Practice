package com.example.practice.controller.userdto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
}
