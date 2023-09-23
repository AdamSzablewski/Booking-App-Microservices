package com.adamszablewski.employmentRequests;

import com.adamszablewski.dto.EmploymentRequestDTO;
import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.employmentRequests.util.UserTools;
import com.adamszablewski.exceptions.NoSuchEmploymentRequestException;
import com.adamszablewski.exceptions.NoSuchFacilityException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.repository.FacilityRepository;
import com.adamszablewski.feignClients.UserRepository;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.feignClients.classes.EmployeeRepository;
import com.adamszablewski.feignClients.classes.UserClass;
import com.adamszablewski.messages.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.adamszablewski.dto.mapper.Mapper.mapEmploymentRequestToDTO;

@AllArgsConstructor
@Service
public class EmploymentRequestService {

    private final MessageSender messageSender;
    private final UserServiceClient userServiceClient;
    private final FacilityRepository facilityRepository;
    private final EmploymentRequestRepository employmentRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final UserTools userTools;

    public void sendEmploymentRequest(String email, long facilityId) {

//        RestResponseDTO<Employee> employeer = userServiceClient.findEmployeeByEmail(email);
        //Employee employee = employeer.getValue();

        Employee employee = userTools.getEmployeeByMail(email);
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

        Employee employee = employmentRequest.getEmployee();
        Facility facility = employmentRequest.getFacility();

        facilityRepository.save(facility);
        if (!status){
            messageSender.sendEmploymentRequestDenied(employee, facility);
        }
        else {
            messageSender.sendEmploymentRequestAccepted(employee, facility);
            facility.getEmployees().add(employee);
            employee.setWorkplace(facility);
            facilityRepository.save(facility);
        }
        employmentRequestRepository.delete(employmentRequest);
    }

    public List<EmploymentRequestDTO> getEmploymentRequestsForUser(long id) {
        Employee employee = employeeRepository.findByUserId(id)
                .orElseThrow(NoSuchUserException::new);

        return mapEmploymentRequestToDTO(employmentRequestRepository.findAllByEmployeeId(employee.getId()));
    }
}
