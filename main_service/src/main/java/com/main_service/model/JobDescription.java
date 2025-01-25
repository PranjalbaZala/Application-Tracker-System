package com.main_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "job_description")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JobDescription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobd_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "JOBD_SEQ", name = "jobd_seq")
    @Column(name = "job_application_id")
    private Integer job_application_id;
    @Column(name = "stream")
    private String stream;
    @Column(name = "designation")
    @Size(min = 2, max = 30)
    private String designation;
    @Column(name = "total_exp_years")
    private Integer totalExpYears;
    @Column(name = "relevant_exp_years")
    private Integer relevantExpYears;
    @Column(name = "old_ctc")
    private Long oldCtc;
    @Column(name = "expected_ctc")
    private Long expectedCtc;
    @Column(name = "notice_period")
    private Integer noticePeriod;
    @Column(name = "role_applied_for")
    private String roleAppliedFor;
    @Column(name = "willing_to_relocate")

    private Boolean willingToRelocate;
    @Column(name = "job_description")
    private String jobDescription;
    @Column(name = "any_current_offer")

    private Boolean anyCurrentOffer;
    @Column(name = "upcoming_interview")

    private Boolean upcomingInterview;
    @Column(name = "reason_for_change")
    private String reasonForChange;
    @Column(name = "referral_source")

    private String referralSource;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uid")
    private User user;

    @Override
    public String toString() {
        return "JobDescription{" +
                "job_application_id=" + job_application_id +
                ", stream='" + stream + '\'' +
                ", designation='" + designation + '\'' +
                ", totalExpYears=" + totalExpYears +
                ", relevantExpYears=" + relevantExpYears +
                ", oldCtc=" + oldCtc +
                ", expectedCtc=" + expectedCtc +
                ", noticePeriod=" + noticePeriod +
                ", roleAppliedFor='" + roleAppliedFor + '\'' +
                ", willingToRelocate=" + willingToRelocate +
                ", jobDescription='" + jobDescription + '\'' +
                ", anyCurrentOffer=" + anyCurrentOffer +
                ", upcomingInterview=" + upcomingInterview +
                ", reasonFoChange='" + reasonForChange + '\'' +
                ", referralSource='" + referralSource + '\'' +
                ", user=" + user +
                '}';
    }
}
