package com.main_service.repository;

import com.main_service.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round,Long> {
}
