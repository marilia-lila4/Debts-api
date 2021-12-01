package io.github.marilialila4.debts.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{campo.name.mandatory}") // Nem nulo e nem vazio
    private String name;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{campo.description.mandatory}")
    private String description;

    @Column
    @NotNull(message = "{campo.value.mandatory}")
    private BigDecimal value;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{campo.status.mandatory}")
    private String status;

    @Column(name = "registration_data", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate registrationData;

    @Column
    private BigDecimal total;

    @PrePersist
    public void prePersist(){
        setRegistrationData(LocalDate.now());
    }
}
