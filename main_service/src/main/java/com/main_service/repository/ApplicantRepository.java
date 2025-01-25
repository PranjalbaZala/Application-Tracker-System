package com.main_service.repository;

import com.main_service.dto.ApplicantTrackingDto;
import com.main_service.dto.ManageUser;
import com.main_service.dto.UserDto;
import com.main_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT new com.main_service.dto.UserDto(u.uid,u.firstname,u.email,u.phoneNumber) " +
            " FROM User u JOIN JobDescription j ON u.uid =j.user.uid " +
            " JOIN ApplicantTracking m ON m.tracking.tid = j.user.uid" +
            " WHERE m.stage = :stage AND m.status = :status AND j.stream = :stream")
    Optional<List<UserDto>> findUsersByStageAndStatusAndStream(@Param("stage") String stage,
                                                               @Param("status") String status,
                                                               @Param("stream") String stream);

    @Query(value = "SELECT new com.main_service.dto.UserDto(u.uid,u.firstname,u.email,u.phoneNumber) " +
            "FROM User u JOIN JobDescription j ON u.uid = j.user.uid " +
            "JOIN ApplicantTracking m ON m.tracking.tid = j.user.uid " +
            "WHERE m.stage =:stage AND j.stream =:stream")
    Optional<List<UserDto>> findUsersByStageAndStream(@Param("stage") String stage,
                                                      @Param("stream") String stream);

    @Query(value = "SELECT new com.main_service.dto.UserDto(u.uid,u.firstname,u.email,u.phoneNumber) " +
            "FROM User u JOIN JobDescription j ON u.uid = j.user.uid " +
            "WHERE j.stream=:stream")
    Optional<List<UserDto>> findUsersByStream(@Param("stream") String stream);

    @Query(value = "SELECT new com.main_service.dto.UserDto(u.uid,u.firstname,u.email,u.phoneNumber)" +
            "FROM User u JOIN JobDescription j ON u.uid = j.user.uid " +
            "JOIN ApplicantTracking m ON m.tracking.tid = j.user.uid WHERE  m.status =:status AND j.stream =:stream")
    Optional<List<UserDto>> findUsersByStatusAndStream(@Param("status") String status,
                                                       @Param("stream") String stream);

    @Query(value = "SELECT new com.main_service.dto.ApplicantTrackingDto(m.status,m.stage,m.review,m.endDate,m.endDate) " +
            " FROM User u JOIN Tracking  t on t.user.uid=u.uid" +
            " join  ApplicantTracking  m on  m.tracking.tid=t.tid" +
            " WHERE u.uid=:uid")
    Optional<List<ApplicantTrackingDto>> findByUserId(@Param("uid") Long userId);
    @Query(value = "SELECT new com.main_service.dto.ManageUser(u.uid,u.firstname, u.phoneNumber ,u.lastname, u.email,u.isActive, r.rolename)\n" +
            " FROM User u\n" +
            " INNER JOIN Role r ON u.role.id = r.id\n" +
            "WHERE r.id NOT IN (1, 3)")
    List<ManageUser> findAllManagedUser();
    @Query(value = "SELECT new com.main_service.dto.UserDto(u.uid,u.firstname,u.lastname, u.email, u.phoneNumber, j.stream,j.roleAppliedFor)\n" +
                   "FROM User u\n" +
                   "LEFT OUTER JOIN Tracking t ON t.user.uid = u.uid OR t.recruiter.uid = u.uid\n" +
                   "JOIN JobDescription j ON j.user.uid = u.uid\n" +
                   "WHERE t.tid IS NULL\n\n")
    Optional<List<UserDto>> findNewUsers();



}
