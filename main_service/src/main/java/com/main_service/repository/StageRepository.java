package com.main_service.repository;

import com.main_service.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StageRepository extends JpaRepository<Stage,Long> {
}
