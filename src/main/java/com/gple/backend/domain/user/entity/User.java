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

    private String name;

    private Long grade;

    private Long classname;

    private Long number;

    private String password;

    @Convert(converter = RoleListConverter.class)
    private List<Role> roles;

    private String profileImage;

    public void setStudentNumber(Long grade, Long classname, Long number){
        this.grade = grade;
        this.classname = classname;
        this.number = number;
    }
}
