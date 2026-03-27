package com.springboot.useradminrolebased.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long userId;

    @Column(nullable = false,name = "Username")
    private String userName;

    @Column(nullable = false,name = "User_Email",unique = true)
    private String userEmail;

    @Column(nullable = false,name = "Password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "User_Role",nullable = false)
    private Role role;
}
