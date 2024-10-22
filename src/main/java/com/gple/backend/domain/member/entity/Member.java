package com.gple.backend.domain.member.entity;

import jakarta.persistence.*;
import com.gple.backend.global.util.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;

    private String email;

    private String password;

    @Convert(converter = StringListConverter.class)
    private List<Role> roles;
}
