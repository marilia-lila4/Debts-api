package io.github.marilialila4.debts.model.repository;

import io.github.marilialila4.debts.model.entity.UserDebt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDebtRepository extends JpaRepository<UserDebt, Integer> {
    Optional<UserDebt> findByUsername(String username);

    boolean existsByUsername(String username);
}
