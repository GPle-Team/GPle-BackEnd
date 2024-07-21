package lib.quick.authservice.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;

    private String email;

    private String username;

    @Setter
    private String name;

    private Role role;
}
