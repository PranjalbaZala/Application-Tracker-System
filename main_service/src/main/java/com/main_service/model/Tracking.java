package com.main_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hr_user_table")
@Builder
public class Tracking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tid")
    private Long tid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiterId")
    private User recruiter;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Override
    public String toString() {
        return "Tracking{" +
                "tid=" + tid +
                ", recruiter=" + recruiter +
                ", user=" + user +
                '}';
    }


}
