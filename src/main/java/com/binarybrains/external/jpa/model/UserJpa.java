package com.binarybrains.external.jpa.model;

import com.binarybrains.core.entity.User;
import jakarta.persistence.*;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tac01_usuario")
public class UserJpa {
    @Id
    @SequenceGenerator(name = "tac01_usuario_id_usuario_seq", sequenceName = "tac01_usuario_id_usuario_seq", allocationSize = 1)
    @GeneratedValue(generator = "tac01_usuario_id_usuario_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id_usuario")
    private Integer id;
    @Column(name = "tx_nombre")
    private String name;
    @Column(name = "tx_primer_apellido")
    private String lastName;
    @Column(name = "tx_segundo_apellido")
    private String secondLastName;
    @Column(name = "tx_login")
    private String email;
    @Column(name = "tx_password")
    private String password;

    public User toEntity() {
        return User.builder()
                .id(id)
                .name(name)
                .lastName(lastName)
                .secondLastName(secondLastName)
                .email(email)
                .password(password)
                .build();
    }

    public static UserJpa fromEntity(User user) {
        return UserJpa.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .secondLastName(user.getSecondLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
