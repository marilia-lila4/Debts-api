package io.github.marilialila4.debts.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CardDTO {

    @NotEmpty(message = "{campo.name.mandatory}")
    private String name;

    @NotEmpty(message = "{campo.description.mandatory}")
    private String description;

    @NotEmpty(message = "{campo.value.mandatory}")
    private String value;

    @NotEmpty(message = "{campo.installments.mandatory}")
    private String installments;

    @NotEmpty(message = "{campo.date.mandatory}")
    private String date;

    @NotEmpty(message = "{campo.status.mandatory}")
    private String status;

    private String total;

    @NotNull(message = "{campo.debt.mandatory}")
    private Integer idDebt;
}
