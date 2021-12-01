package io.github.marilialila4.debts.model.repository;

import io.github.marilialila4.debts.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {

    @Query(" select c from Card c join c.debt d " +
            "where upper( d.name ) like upper( :name ) and MONTH(c.date) =:month ")
    List<Card> findByNameAndMonth(
            @Param("name") String name, @Param("month") Integer month);
}
