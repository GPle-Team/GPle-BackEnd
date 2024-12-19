package com.gple.backend.domain.user.entity;

import jakarta.persistence.*;
import com.gple.backend.global.util.RoleListConverter;
import lombok.*;

import java.util.List;

@Getter
@Setter
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

    private String profileImage;

    public void setStudentProfile(String username, String studentNumber, String profileImage){
        this.username = username;
        this.studentNumber = studentNumber;
        this.profileImage = profileImage;
    }
}
