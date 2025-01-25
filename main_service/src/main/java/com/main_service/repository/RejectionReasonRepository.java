package com.main_service.repository;

import com.main_service.model.RejectionReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejectionReasonRepository extends JpaRepository<RejectionReason,Long> {

}
