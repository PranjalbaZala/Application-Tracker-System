package com.main_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "stage")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stage_id")
    private Long stageId;

    @Column(name = "stage_name")
    @NotNull
    private String stageName;

    @Column(name = "created_time")
    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss",iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive;

    //one Stage has many rounds
    @OneToMany(cascade = CascadeType.ALL,targetEntity = Round.class )
    @JoinColumn(name ="s_id")
    private List<Round> round;

    @Override
    public String toString() {
        return "Stage{" +
                "streamId=" + stageId +
                ", stageName='" + stageName + '\'' +
                ", createdTime=" + createdTime +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }

}
