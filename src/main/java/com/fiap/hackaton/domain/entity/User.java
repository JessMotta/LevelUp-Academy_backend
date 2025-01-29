package com.fiap.hackaton.domain.entity;

import com.fiap.hackaton.domain.dto.user.UserRequest;
import com.fiap.hackaton.domain.enums.Roles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
@EqualsAndHashCode(of = "id")
@Schema(hidden = true)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles role;

    public User(UserRequest request) {
        this.name = request.name();
        this.email = request.email();
        this.password = request.password();
    }

    public void update(UserRequest request) {
        if (request.name() != null) this.name = request.name();
        if (request.email() != null) this.email = request.email();
        if (request.password() != null) this.password = request.password();
    }

}
