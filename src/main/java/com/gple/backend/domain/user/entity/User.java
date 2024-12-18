package com.gple.backend.domain.user.entity;

import jakarta.persistence.*;
import com.gple.backend.global.util.RoleListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    private String username;

    private String studentNumber;

    private String password;

    @Convert(converter = RoleListConverter.class)
    private List<Role> roles;

    public void setStudentProfile(String username, String studentNumber){
        this.username = username;
        this.studentNumber = studentNumber;
    }
}
