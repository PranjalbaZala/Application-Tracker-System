package com.main_service.repository;

import com.main_service.model.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StreamRepository extends JpaRepository<Stream,Long> {
}
