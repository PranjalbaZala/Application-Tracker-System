package com.main_service.controller;

import com.main_service.dto.*;
import com.main_service.model.*;
import com.main_service.service.AdminService;
import com.main_service.service.PermissionService;
import com.main_service.service.RoleServices;
import com.main_service.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path = "/main/api/admin")
public class AdminResource {
    @Autowired
    private AdminService service;
    @Autowired
    private RoleServices roleServices;
    @Autowired
    private PermissionService permissionServices;
    @Autowired
    private StreamService streamService;

    @PostMapping(path = "createTracking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createState(@RequestBody @Valid ApplicantTracking trackingRequest) {
        service.createState(trackingRequest);
        return ResponseEntity.ok("created state successfully");
    }

    @PostMapping(path = "updateTracking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateTracking(@RequestBody @Valid ApplicantTracking trackingRequest) {
        service.updateStatus(trackingRequest);
        return ResponseEntity.ok("updated tracking successfully");
    }

    @GetMapping(path = "user/trackingId/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Long> getUserTrackingId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findTracking(id));
    }

    @GetMapping(path = "get/{stage}/{status}/{stream}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<UserDto>> getUsersByStageAndStatusAndStream(
            @PathVariable("stage") String stage,
            @PathVariable("status") String status,
            @PathVariable("stream") String stream) {

        return ResponseEntity.ok(service.getUsersByStageAndStatusAndStream(stage, status, stream));
    }

    @GetMapping(path = "{stage}/{stream}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<UserDto>> getUsersByStageAndStream(
            @PathVariable("stage") String stage,
            @PathVariable("stream") String stream) {
        return ResponseEntity.ok(service.getUsersByStageAndStream(stage, stream));
    }

    @GetMapping(path = "get/{status}/{stream}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<UserDto>> getUsersByStatusAndStream(@PathVariable("status") String status, @PathVariable("stream") String stream)  {
        return ResponseEntity.ok(service.getUsersByStatusAndStream(status, stream));
    }

    @GetMapping(path = "get/{stream}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<UserDto>> getUsersByStream(@PathVariable("stream") String stream) {
        return ResponseEntity.ok(service.getUsersByStream(stream));
    }

    @GetMapping(path = "getAllUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<AllApplicantDataDto>>> getAllUser() {
        return ResponseEntity.ok(service.getAllUser());
    }

    @PostMapping(path = "/addRole", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveRole(@RequestBody @Valid RoleDto role) {
        roleServices.savaRole(role);
        return ResponseEntity.ok("Role added successfully");
    }

    @PostMapping(path = "/addPermission", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> savePermission(@RequestBody Permission permission) {
        permissionServices.savePermission(permission);
        return ResponseEntity.ok("permission added successfully");
    }

    @PostMapping(path = "/updateRole", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateRole(@RequestBody RoleDto roleRequest) {
        roleServices.updateRole(roleRequest);
        return ResponseEntity.ok("Role updated successfully");
    }

    @PostMapping(path = "/updatePermission/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updatePermission(@RequestBody @Valid Permission permissionRequest, @PathVariable Integer id) {
        permissionServices.updatePermission(permissionRequest, id);
        return ResponseEntity.ok("Permission updated successfully");
    }

    @GetMapping(path = "/getAllPermission", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<Permission>>> getAllPermission() {
        return ResponseEntity.ok(permissionServices.getAllPermissions());
    }

    @GetMapping(path = "/getAllRole", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<Role>>> getAllRole() {
        return ResponseEntity.ok(roleServices.getAllRole());
    }


    //stream api
    @GetMapping("/getAllStream")
    public ResponseEntity<Optional<List<?>>> getAllStream() {
        return ResponseEntity.ok(streamService.getAllStream());
    }

    @PostMapping(path = "addStream", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addStream(@RequestBody Stream stream) {
        streamService.addStream(stream);
        return ResponseEntity.ok("stream created");
    }

    @PutMapping(path = "/updateStream/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Stream> updateStream(@PathVariable("id") Long id, @RequestBody Stream updatedStream) throws Exception {
        updatedStream.setStreamId(id);
        Stream updated = streamService.updateStream(updatedStream);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/deleteStream/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> deleteStream(@PathVariable Long id) {
        streamService.deleteStream(id);
        return ResponseEntity.ok("Delete Stream Successfully");
    }

    //Rejection reason API
    @GetMapping(path = "/getAllRejectionreasons")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<RejectionReason> getAllRejectionReasons() {
        return service.getAllRegistrationReasons();
    }

    @GetMapping(path = "/getRegectionreason/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RejectionReason getRejectionReasonById(@PathVariable Long id) throws Exception {
        return service.getRejectionReasonById(id);
    }

    @PutMapping("rejection/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<RejectionReason> updateRejectionReason(@PathVariable("id") Long id, @RequestBody RejectionReason updatedRejectionReason) throws Exception {
        updatedRejectionReason.setRejectionId(id);
        RejectionReason updated = service.updateRejectionReason(updatedRejectionReason);
        return ResponseEntity.ok(updated);
    }

    @PostMapping(path = "/addRejectionReason")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRegectionReason(@RequestBody RejectionReason rejectionReason) {
        service.saveRejectionReason(rejectionReason);

    }

    //manage user
    @GetMapping(path = "/getManagedUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<?>>> getManagedUser() {
        return ResponseEntity.ok(service.getManagedUser());
    }

//    @PutMapping("updateManagedUser/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public ResponseEntity<?> updateManagedUser(@PathVariable("id") Long id, @RequestBody ManageUser updatedManageduser) throws Exception {
//        updatedManageduser.setRejectionId(id);
//        RejectionReason updated = service.updateRejectionReason(updatedRejectionReason);
//        return ResponseEntity.ok(updated);
//    }

    @GetMapping(path = "/getAllStage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<Stage>>> getAllStage() {
        return ResponseEntity.ok(service.getAllStage());
    }

    @PostMapping(path = "/addStage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveStage(@RequestBody Stage stage) {
        service.saveStage(stage);
        return ResponseEntity.ok("Stage added successfully");
    }

    @PostMapping(path = "/addRound", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveRound(@RequestBody Round round) {
        service.saveRound(round);
        return ResponseEntity.ok("Round added successfully");
    }

    @PostMapping(path = "/BulkUpdateFeedback", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ApplicantTracking>> bulkUpdateFeedback(@RequestBody MainBulkUpdateDto mainBulkUpdateDto) {
//        System.out.println(service.bulkupdateFeedback(mainBulkUpdateDto));
        List<ApplicantTracking> demo = new ArrayList<>();
        demo = service.bulkupdateFeedback(mainBulkUpdateDto);
        return ResponseEntity.ok(demo);

    }

    @PostMapping(path = "/BulkUpdateCreate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ApplicantTracking>> bulkUpdateCreate(@RequestBody MainUpdateCreate mainUpdateCreate) {
//        System.out.println(service.bulkupdateFeedback(mainBulkUpdateDto));
        List<ApplicantTracking> demo = new ArrayList<>();
        demo = service.bulkupdateCreate(mainUpdateCreate);
        return ResponseEntity.ok(demo);

    }

    @GetMapping(path = "/candidatesSelectionToRejection", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, Object>> getSelectionToRejection() {
        Map<String, Object> data = new HashMap<>();
        data = service.selectionToRejection();
        return ResponseEntity.ok(data);
    }

    @GetMapping(value = "/stream-count")
    public ResponseEntity<Map<String, Integer>> getStreamCount() {
        Map<String, Integer> streamCountMap = new HashMap<>();
        List<Object[]> results = service.countApplicantsByStream();

        for (Object[] result : results) {
            String stream = (String) result[0];
            Long count = (Long) result[1];
            streamCountMap.put(stream, count.intValue());
        }
        return ResponseEntity.ok(streamCountMap);
    }


    @GetMapping(path = "/getNewUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Optional<List<UserDto>>> getNewUser() {
        Optional<List<UserDto>> roles = service.getNewUser();
        return ResponseEntity.ok(roles);
    }
}
