package io.github.marilialila4.debts.model.repository;

import io.github.marilialila4.debts.model.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
}
