package com.main_service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BulkUpdateId implements Serializable {
    private Long id;
    private Long tid;
}
