package com.adamszablewski.facilities.employmentRequests;

import com.adamszablewski.exceptions.NoSuchFacilityException;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.repository.FacilityRepository;
import com.adamszablewski.facilities.service.FacilityService;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.messages.MessageSender;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class EmploymentRequestService {

    private final MessageSender messageSender;
    private final UserServiceClient userServiceClient;
    private final FacilityRepository facilityRepository;
    private final EmploymentRequestRepository employmentRequestRepository;

    public void sendEmploymentRequest(String email, long facilityId) {
        Employee employee = userServiceClient.findEmployeeByEmail(email).getValue();
        Facility facility = facilityRepository.findById(facilityId)
                        .orElseThrow(NoSuchFacilityException::new);

        EmploymentRequest employmentRequest = EmploymentRequest.builder()
                .employee(employee)
                .facility(facility)
                .status(false)
                .build();

        employmentRequestRepository.save(employmentRequest);

        messageSender.sendEmploymentRequestMessage(employee, facility);


    }

    public List<Facility> acceptEmploymentRequest(long id, boolean status) {
        
    }
}
