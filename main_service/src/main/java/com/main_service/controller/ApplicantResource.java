package com.main_service.controller;

import com.main_service.dto.AddressDto;
import com.main_service.dto.ApplicantTrackingDto;
import com.main_service.dto.JobApplicationDetailsDto;
import com.main_service.dto.updateAddressDto;
import com.main_service.model.Address;
import com.main_service.model.JobDescription;
import com.main_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/main/api/user")
public class ApplicantResource {
    @Autowired
    private UserService service;


    @PostMapping(path="/addJobApplicationDetails",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addJobApplicantDetails(@RequestBody JobApplicationDetailsDto jobdto){
        service.addJobApplicationDetails(jobdto);
    }
    @GetMapping(path = "current/status/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ApplicantTrackingDto> userTracking(@PathVariable Long id){
        return service.getApplicantTracking(id).orElse(null);
    }
    @GetMapping(path="/GetAllJobApplication")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<JobDescription>> GetAllJobApplication() throws Exception {
        List<JobDescription> jobData = new ArrayList<>();
        jobData=service.getAllJobApplication();
        return ResponseEntity.ok(jobData);
    }

    @RequestMapping(value = "/getAddress/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<List<Address>>> getAddressById(@PathVariable Long id) throws Exception {
        Optional<List<Address>> details = service.getAddressByuserId(id);
        return ResponseEntity.ok(details);
    }

    @PutMapping("/updateAddress")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateAddress(@RequestBody updateAddressDto updateAddDto) {
        service.updateAddressById(updateAddDto);
    }

    @PostMapping(path = "/addAddress",consumes = "application/*",produces = "application/*")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAddress(@RequestBody AddressDto addAddressDto){
        service.addAddressByUserID(addAddressDto);
    }

}
