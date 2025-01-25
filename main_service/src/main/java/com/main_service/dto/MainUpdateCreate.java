package com.main_service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MainUpdateCreate implements Serializable {

    BulkUpdateCreate data;

    List<BulkUpdateCreateId> ids;
}
