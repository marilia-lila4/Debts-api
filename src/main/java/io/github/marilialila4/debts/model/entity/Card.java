package io.github.marilialila4.debts.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,  length = 150)
    private String name;

    @Column(nullable = false, length = 150)
    private String description;

    @Column
    private BigDecimal value;

    @Column(nullable = false, length = 10)
    private String installments;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @Column(nullable = false, length = 100)
    private String status;

    @Column
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "id_debt")
    private Debt debt;
}
