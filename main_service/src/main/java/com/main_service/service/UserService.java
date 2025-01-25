package com.main_service.service;

import com.main_service.dto.AddressDto;
import com.main_service.dto.ApplicantTrackingDto;
import com.main_service.dto.JobApplicationDetailsDto;
import com.main_service.dto.updateAddressDto;
import com.main_service.model.Address;
import com.main_service.model.JobDescription;
import com.main_service.repository.AddressRepository;
import com.main_service.repository.ApplicantRepository;
import com.main_service.repository.JobDescriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;


@Service
@Configuration
@Slf4j
public class UserService {
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private JobDescriptionRepository jobDescriptionRepository;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Optional<List<ApplicantTrackingDto>> getApplicantTracking(Long id) {
        return applicantRepository.findByUserId(id);
    }

    public void addJobApplicationDetails(JobApplicationDetailsDto jobdto) {
        JobDescription jobdetails = this.modelMapper.map(jobdto, JobDescription.class);
        System.out.printf("" + jobdetails.toString());
        jobDescriptionRepository.save(jobdetails);
        log.info("Job application details {} saved", jobdetails);
    }

    public List<JobDescription> getAllJobApplication() throws Exception {
        Optional<List<JobDescription>> jobDescription = Optional.of(jobDescriptionRepository.findAll());
//        System.out.println(jobOpt.toString());
        if (!jobDescription.get().isEmpty()) {
            System.out.print(jobDescription.toString());
            return jobDescription.get();
        } else {
            throw new Exception("Data not found");
        }
    }
    public void addAddressByUserID(AddressDto address) {
        Address add = this.modelMapper.map(address, Address.class);
        System.out.printf("" + add.toString());
        addressRepository.save(add);
        log.info("Address {} saved", add);
    }


    public Optional<List<Address>> getAddressByuserId(Long uid) throws Exception {
        Optional<List<Address>> addopt = Optional.ofNullable(addressRepository.findAllByUserId(uid));
        System.out.println(addopt.toString());
        if (addopt.get().isEmpty()) {
            throw new Exception("id not found");
        } else {
            System.out.print(addopt.toString());
            return addopt;

        }
    }

    public void updateAddressById(updateAddressDto dto) {
        addressRepository.updateUserById(dto.getUser().getUid(), dto.getStreet(),
                dto.getCity(), dto.getState(),
                dto.getCountry(), dto.getPincode()
                , dto.getAddressType(),dto.getAddress().getAddressId());
        // Address addu = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }
}


