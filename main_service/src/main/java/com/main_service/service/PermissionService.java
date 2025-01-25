package com.main_service.service;

import com.main_service.model.Permission;
import com.main_service.repository.PermissionRepository;
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

@Slf4j
@Service
@EnableTransactionManagement
@Configuration
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public void savePermission(Permission permissionRequest) {

        Permission permission = Permission.builder()
                .name(permissionRequest.getName())
                .createdAt(permissionRequest.getCreatedAt())
                .createdBy(permissionRequest.getCreatedBy())
                .build();

        permissionRepository.save(permission);
        log.info("complete permission {}", permission);
    }
    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Optional<List<Permission>> getAllPermissions() {
        Optional<List<Permission>> permissions = Optional.of(permissionRepository.findAll());
        return Optional.of(permissions.orElse(null));
    }
    @Transactional(propagation = Propagation.NESTED, rollbackFor = {SQLException.class, ConfigDataNotFoundException.class}, isolation = Isolation.READ_COMMITTED)
    public void updatePermission(Permission permissionRequest, Integer id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        if (permission.isPresent()) {
            permission = Optional.ofNullable(Permission.builder()
                    .name(permissionRequest.getName())
                    .updatedAt(permissionRequest.getUpdatedAt())
                    .build());
        }
        permission.ifPresent(permissionRepository::save);
    }
}
