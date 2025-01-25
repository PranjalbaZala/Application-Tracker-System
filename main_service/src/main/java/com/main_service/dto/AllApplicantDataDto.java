package com.main_service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
public class AllApplicantDataDto implements Serializable {
    private Long id;
    private Long trackingId;
    private String name;
    private String status;
    private String stage;
    private String stream;
    public AllApplicantDataDto(Long id,Long trackingId, String name, String status, String stage, String stream) {
        this.id = id;
        this.trackingId = trackingId;
        this.name = name;
        this.status = status;
        this.stage = stage;
        this.stream = stream;
    }
}
