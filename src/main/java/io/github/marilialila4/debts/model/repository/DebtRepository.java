package io.github.marilialila4.debts.model.repository;

import io.github.marilialila4.debts.model.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository extends JpaRepository<Debt, Integer>{

    /*
    FALTA FAZER:
    Realizar a soma de todos os valores da dividas

    @Query(value = "select SUM(d.value) as Soma from debt d", nativeQuery = true)
    List<Saving> sumTotalSaving(@Param("soma") Integer soma);
     */
}
