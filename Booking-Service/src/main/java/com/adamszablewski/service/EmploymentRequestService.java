package com.adamszablewski.service;

import com.adamszablewski.dto.EmploymentRequestDTO;
import com.adamszablewski.exceptions.NotAuthorizedException;
import com.adamszablewski.util.helpers.UserTools;
import com.adamszablewski.exceptions.NoSuchEmploymentRequestException;
import com.adamszablewski.exceptions.NoSuchFacilityException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.model.Facility;
import com.adamszablewski.repository.FacilityRepository;
import com.adamszablewski.model.Employee;
import com.adamszablewski.repository.EmployeeRepository;
import com.adamszablewski.messages.MessageSender;
import com.adamszablewski.model.EmploymentRequest;
import com.adamszablewski.repository.EmploymentRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Set;

import static com.adamszablewski.dto.mapper.Mapper.mapEmploymentRequestToDTO;

@AllArgsConstructor
@Service
public class EmploymentRequestService {

    private final MessageSender messageSender;
    private final FacilityRepository facilityRepository;
    private final EmploymentRequestRepository employmentRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final UserTools userTools;


    public void sendEmploymentRequest(String email, long facilityId) {

        Employee employee = userTools.getEmployeeByMail(email);
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

    public void answerEmploymentRequest(long id, boolean status, String userEmail) {
        EmploymentRequest employmentRequest = employmentRequestRepository.findById(id)
                .orElseThrow(NoSuchEmploymentRequestException::new);

        Employee employee = employmentRequest.getEmployee();
        Facility facility = employmentRequest.getFacility();

        if (!employmentRequest.getEmployee().getUser().getEmail().equals(userEmail)){
            throw new NotAuthorizedException();
        }

       // facilityRepository.save(facility);
        if (!status){
            messageSender.sendEmploymentRequestDenied(employee, facility);
        }
        else {
            messageSender.sendEmploymentRequestAccepted(employee, facility);
            facility.getEmployees().add(employee);
            employee.setWorkplace(facility);
            employee.setStartTime(LocalTime.of(9, 0));
            employee.setEndTime(LocalTime.of(17, 0));
            employeeRepository.save(employee);
            facilityRepository.save(facility);
        }
        employmentRequestRepository.delete(employmentRequest);
    }

    public Set<EmploymentRequestDTO> getEmploymentRequestsForUser(long id, String userEmail) {
        Employee employee = employeeRepository.findByUserId(id)
                .orElseThrow(NoSuchUserException::new);

        return mapEmploymentRequestToDTO(employmentRequestRepository.findAllByEmployeeId(employee.getId()));
    }
}
