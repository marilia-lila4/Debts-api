package io.github.marilialila4.debts.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
public class UserDebt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @NotEmpty(message = "{campo.username.mandatory}")
    private String username;

    @Column()
    @NotEmpty(message = "{campo.password.mandatory}")
    private String password;

}
