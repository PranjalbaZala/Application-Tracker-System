package com.main_service.repository;

import com.main_service.dto.AllApplicantDataDto;
import com.main_service.dto.ManageUser;
import com.main_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManageUserRepository extends JpaRepository<User, Long> {



}
