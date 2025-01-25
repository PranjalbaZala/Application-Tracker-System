package com.main_service.service;

import com.main_service.advice.Constant;
import com.main_service.dto.*;
import com.main_service.model.*;
import com.main_service.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@Configuration
@EnableTransactionManagement
public class AdminService {
    @Autowired
    private ApplicantTrackingRepository applicantTrackingRepository;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private TrackingRepository trackingRepository;
    @Autowired
    private RejectionReasonRepository rejectionReasonRepository;
    @Autowired
    private ManageUserRepository manageUserRepository;
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private JobDescriptionRepository jobDescriptionRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public void createState(ApplicantTracking trackingRequest) {

        Tracking tracking = trackingRepository.save(trackingRequest.getTracking());
        ApplicantTracking applicantTracking = ApplicantTracking.builder()
                .stage(trackingRequest.getStage())
                .round(trackingRequest.getRound())
                .status(trackingRequest.getStatus())
                .tracking(tracking)
                .build();

        applicantTrackingRepository.save(applicantTracking);
        log.info("Tracking saved successfully {}", tracking);

    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {SQLException.class, ConfigDataNotFoundException.class}, isolation = Isolation.READ_COMMITTED)
    public void updateStatus(ApplicantTracking trackingRequest) {
        ApplicantTracking tracking = ApplicantTracking.builder()
                .id(trackingRequest.getId())
                .status(trackingRequest.getStatus())
                .round(trackingRequest.getRound())
                .stage(trackingRequest.getStage())
                .review(trackingRequest.getReview())
                .tracking(trackingRequest.getTracking())
                .build();

        applicantTrackingRepository.save(tracking);
        log.info("updateStatus status Success");

    }

    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Long findTracking(Integer userId) {
        Optional<Tracking> tracking = trackingRepository.findByTrackingID(userId);
        log.info("Tracking {}", tracking);
        return tracking.map(Tracking::getTid).orElse(null);
    }

    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public List<UserDto> getUsersByStageAndStatusAndStream(String stage, String status, String stream) {
        return applicantRepository.findUsersByStageAndStatusAndStream(stage, status, stream).orElse(null);
    }

    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public List<UserDto> getUsersByStageAndStream(String stage, String stream) {
        return applicantRepository.findUsersByStageAndStream(stage, stream).orElse(null);
    }

    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public List<UserDto> getUsersByStream(String stream) {
        System.out.println(applicantRepository.findUsersByStream(stream));
        return applicantRepository.findUsersByStream(stream).orElse(null);
    }

    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public List<UserDto> getUsersByStatusAndStream(String status, String stream) {
        return applicantRepository.findUsersByStatusAndStream(status, stream).orElse(null);
    }

    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Optional<List<AllApplicantDataDto>> getAllUser() {
        System.out.println(trackingRepository.findAllByUser());
        return trackingRepository.findAllByUser();
    }

    // rejection reason 
    public List<RejectionReason> getAllRegistrationReasons() {
        return rejectionReasonRepository.findAll();
    }

    public void saveRejectionReason(RejectionReason RejectionReason) {
        RejectionReason reason = RejectionReason
                .builder()
                .reason(RejectionReason.getReason())
                .userId(RejectionReason.getUserId())
                .build();

        rejectionReasonRepository.save(reason);
        log.info("Rejection reason {} saved", reason);
    }

    public RejectionReason getRejectionReasonById(Long id) throws Exception {
        //   Predicate<? super RejectionReason> predicate = rejectionReason -> rejectionReason.getR_id().equals(id);
        //    return rejectionReasonRepository.s

        Optional<RejectionReason> optionalUser = rejectionReasonRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new Exception("user not found");
//            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    public RejectionReason updateRejectionReason(RejectionReason rejectionReason) throws Exception {
        // Check if rejection reason exists
        Optional<RejectionReason> existingRejectionReasonOptional = rejectionReasonRepository.findById(rejectionReason.getRejectionId());
        if (!existingRejectionReasonOptional.isPresent()) {
            throw new Exception("rejection reason not found");
//            throw new NotFoundException("Rejection reason not found with id " + rejectionReason.getR_id());
        }

        // Update the fields of the existing rejection reason
        RejectionReason existingRejectionReason = existingRejectionReasonOptional.get();
        existingRejectionReason.setReason(rejectionReason.getReason());

        // Save the updated rejection reason to the repository
        RejectionReason updatedRejectionReason = rejectionReasonRepository.save(existingRejectionReason);

        return updatedRejectionReason;
    }

    // getmanageduser
    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Optional<List<?>> getManagedUser() {
        return Optional.ofNullable(applicantRepository.findAllManagedUser());
    }

    @Transactional(rollbackFor = ConfigDataNotFoundException.class, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Optional<List<Stage>> getAllStage() {
        Optional<List<Stage>> stage = Optional.of(stageRepository.findAll());
        return Optional.of(stage.orElse(null));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public void saveStage(Stage stageRequest) {
        Stage stage = Stage.builder()
                .stageName(stageRequest.getStageName())
                .isActive(Constant.True)
                .round(stageRequest.getRound())
                .createdBy(stageRequest.getCreatedBy())
                .createdTime(stageRequest.getCreatedTime())
                .build();

        stageRepository.save(stage);
        log.info("Stage added {}", stage);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public void saveRound(Round roundRequest) {
        Round round = Round.builder()
                .roundName(roundRequest.getRoundName())
                .createdTime(roundRequest.getCreatedTime())
                .createdBy(roundRequest.getCreatedBy())
                .build();

        roundRepository.save(round);
        log.info("Round added {}", round);
    }

    public List<ApplicantTracking> bulkupdateFeedback(MainBulkUpdateDto mainBulkUpdateDto) {
        List<BulkUpdateId> bulkUpdateIds = mainBulkUpdateDto.getIds();

        List<ApplicantTracking> applicantTrackings = new ArrayList<>();


        BulkUpdateData bulkUpdateData = mainBulkUpdateDto.getData();

        System.out.printf(bulkUpdateData.toString());

        for (BulkUpdateId bulkUpdateId : bulkUpdateIds) {
            Long id = bulkUpdateId.getId();
            if (applicantTrackingRepository.findById(id).isPresent()) {
                applicantTrackings.add(applicantTrackingRepository.findById(id).get());
            }
        }

        for (ApplicantTracking applicantTracking : applicantTrackings) {
            applicantTracking.setEndDate(bulkUpdateData.getEndDate());
            applicantTracking.setReview(bulkUpdateData.getReview());
            applicantTracking.setStatus(bulkUpdateData.getStatus());
            applicantTracking.setFutureRef(bulkUpdateData.getFutureRef());
        }
        for (ApplicantTracking tracking : applicantTrackings) {
            applicantTrackingRepository.saveAndFlush(tracking);
        }
        return applicantTrackings;
    }

    public List<ApplicantTracking> bulkupdateCreate(MainUpdateCreate mainUpdateCreate) {
        List<BulkUpdateCreateId> bulkUpdateCreateIds = mainUpdateCreate.getIds();

        BulkUpdateCreate bulkUpdateCreates = mainUpdateCreate.getData();

        List<ApplicantTracking> applicantTrackings = new ArrayList<>();

//        System.out.printf(bulkUpdateData.toString());

        for (BulkUpdateCreateId bulkUpdateCreateId : bulkUpdateCreateIds) {
            ApplicantTracking applicantTracking = new ApplicantTracking();
            Tracking tracking = new Tracking();
            tracking.setTid(bulkUpdateCreateId.getTrackingId());
            applicantTracking.setTracking(tracking);
            applicantTracking.setStartDate(bulkUpdateCreates.getStartDate());
            applicantTracking.setStage(bulkUpdateCreates.getStage());
            applicantTracking.setRound(bulkUpdateCreates.getRound());

            applicantTracking.setStatus(bulkUpdateCreates.getStatus());
            applicantTrackings.add(applicantTracking);
            applicantTrackingRepository.save(applicantTracking);
        }
        return applicantTrackings;
    }

    public List<Integer> getOfferedRejected() {
        Integer offered = applicantTrackingRepository.countByStatus("Offered");
        Integer rejected = applicantTrackingRepository.countByStatus("Rejected");
        Integer BackedOut = applicantTrackingRepository.countByStatus("BackedOut");
        List<Integer> offrej = new ArrayList<>();
        offrej.add(0, offered);
        offrej.add(1, rejected);
        offrej.add(2, BackedOut);
        return offrej;
    }

    public Map<String, Object> selectionToRejection() {
        List<Integer> CountOffRejBack = getOfferedRejected();

        List<Object[]> data = applicantTrackingRepository.countByStream();

        List<Map<String, Object>> streamData = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> map = new HashMap<>();
            map.put("stream", row[0]);
            map.put("selected", row[1]);
            map.put("rejected", row[2]);
            map.put("BackedOut", row[3]);
            streamData.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("offered", CountOffRejBack.get(0));
        result.put("rejected", CountOffRejBack.get(1));
        result.put("BackedOut", CountOffRejBack.get(2));
        result.put("streamData", streamData);

        return result;

    }

    public List<Object[]> countApplicantsByStream() {
        List<Object[]> results = jobDescriptionRepository.countApplicantsByStream();
        return results;
    }

    public Optional<List<UserDto>> getNewUser() {
        System.out.println(applicantRepository.findNewUsers());
        return applicantRepository.findNewUsers();
    }
}
