package io.github.marilialila4.debts.model.repository;

import io.github.marilialila4.debts.model.entity.Card;
import io.github.marilialila4.debts.model.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingRepository extends JpaRepository<Saving, Integer> {

    /*
    FALTA FAZER:
    Realizar a soma de todos os valores da poupança

    @Query(value = "select SUM(s.value) as Soma from saving s", nativeQuery = true)
    List<Saving> sumTotalSaving(@Param("soma") Integer soma);
     */
}