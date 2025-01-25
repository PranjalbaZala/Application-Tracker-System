package com.main_service.service;

import com.main_service.dto.RoleDto;
import com.main_service.model.Role;
import com.main_service.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@EnableTransactionManagement
@Slf4j
@Configuration
public class RoleServices {
    @Autowired
    private RoleRepository roleRepository;
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public void savaRole(RoleDto roleRequest) {
        Role role = Role.builder()
                .rolename(roleRequest.getRolename())
                .permissions(roleRequest.getPermissions())
                .createdAt(roleRequest.getCreatedAt())
                .createdBy(roleRequest.getCreatedBy())
                .build();
        roleRepository.save(role);
        log.info("Role saved successfully{}", role);
    }
    @Transactional(propagation = Propagation.NESTED, rollbackFor = {SQLException.class, ConfigDataNotFoundException.class}, isolation = Isolation.READ_COMMITTED)
    public void updateRole(RoleDto roleRequest) {
        Role role = Role.builder()
                .id(roleRequest.getId())
                .rolename(roleRequest.getRolename())
                .updatedAt(roleRequest.getUpdatedAt())
                .permissions(roleRequest.getPermissions())
                .updatedBy(roleRequest.getUpdatedBy())
                .build();
        roleRepository.save(role);
        log.info("Role updated successfully");
    }
    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Optional<List<Role>> getAllRole() {
        Optional<List<Role>> roles = Optional.of(roleRepository.findAll());
        return Optional.of(roles.orElse(null));
    }
}
