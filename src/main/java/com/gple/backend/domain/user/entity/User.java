package com.gple.backend.domain.user.entity;

import jakarta.persistence.*;
import com.gple.backend.global.util.StringListConverter;
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

    private String email;

    private String password;

    @Convert(converter = StringListConverter.class)
    private List<Role> roles;
}
