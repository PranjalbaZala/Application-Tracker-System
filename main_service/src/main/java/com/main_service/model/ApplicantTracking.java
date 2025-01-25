package com.main_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mainTable")
@Builder
public class ApplicantTracking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "stage", updatable = false)
    private String stage;

    @Column(name = "startDate",updatable = false)
    @CreationTimestamp
    private LocalDateTime startDate;

    @Column(updatable = false)
    private String round;

    @Column Boolean futureRef;

    @Column(name = "endDate")
    @UpdateTimestamp
    private LocalDateTime endDate;
    @Column(name = "review")
    private String review;
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Tracking.class)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "trackingID", referencedColumnName ="tid")
    private Tracking tracking;
}
