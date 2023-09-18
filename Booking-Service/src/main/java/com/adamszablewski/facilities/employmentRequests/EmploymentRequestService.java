package com.adamszablewski.facilities.employmentRequests;

import com.adamszablewski.appointments.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.NoSuchEmploymentRequestException;
import com.adamszablewski.exceptions.NoSuchFacilityException;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.repository.FacilityRepository;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.messages.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class EmploymentRequestService {

    private final MessageSender messageSender;
    private final UserServiceClient userServiceClient;
    private final FacilityRepository facilityRepository;
    private final EmploymentRequestRepository employmentRequestRepository;

    public void sendEmploymentRequest(String email, long facilityId) {

        RestResponseDTO<Employee> employeer = userServiceClient.findEmployeeByEmail(email);
        Employee employee = employeer.getValue();
        System.out.println("rest resp "+employeer);

        System.out.println("service "+employee);
        Facility facility = facilityRepository.findById(facilityId)
                        .orElseThrow(NoSuchFacilityException::new);

        EmploymentRequest employmentRequest = EmploymentRequest.builder()
                .employee(employee)
                .facility(facility)
                .status(false)
                .build();

        messageSender.sendEmploymentRequestMessage(employee, facility);
        employmentRequestRepository.save(employmentRequest);

    }

    public void answereEmploymentRequest(long id, boolean status) {
        EmploymentRequest employmentRequest = employmentRequestRepository.findById(id)
                .orElseThrow(NoSuchEmploymentRequestException::new);
        employmentRequest.setStatus(status);
        employmentRequestRepository.save(employmentRequest);

        Employee employee = employmentRequest.getEmployee();
        Facility facility = employmentRequest.getFacility();
        facility.getEmployees().add(employee);
        facilityRepository.save(facility);
        if (!status){
            messageSender.sendEmploymentRequestDenied(employee, facility);
        }
        else {
            messageSender.sendEmploymentRequestAccepted(employee, facility);
        }
        employmentRequestRepository.delete(employmentRequest);
    }

    public void createEmploymentRequest(EmploymentRequest employmentRequest) {
    }
}
