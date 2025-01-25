package com.main_service.repository;

import com.main_service.dto.AllApplicantDataDto;
import com.main_service.model.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    @Query(value = "SELECT * FROM hr_user_table h WHERE h.user_id=:user_id", nativeQuery = true)
    Optional<Tracking> findByTrackingID(@Param("user_id") Integer userid);

    @Query(value = "SELECT new com.main_service.dto.AllApplicantDataDto(mt.id,t.tid,u.firstname,mt.status,mt.stage,j.stream )\n" +
            "FROM  Tracking t\n" +
            "JOIN t.user u \n" +
            "JOIN JobDescription j ON t.user.uid = j.user.uid\n" +
            "JOIN ApplicantTracking mt ON t.tid = mt.tracking.tid AND mt.id = (" +
            "  SELECT MAX(mt2.id)\n" +
            "  FROM ApplicantTracking mt2\n" +
            "  WHERE mt2.tracking.tid = mt.tracking.tid\n" +
            ") ")
    Optional<List<AllApplicantDataDto>> findAllByUser();


//    public ApplicantTrackingDto(String status, String stage, String review,
//                                LocalDate startDate, LocalDate endDate, Tracking tracking) {
//        this.status = status;
//        this.stage = stage;
//        this.review = review;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.tracking = tracking;
//    }


}
