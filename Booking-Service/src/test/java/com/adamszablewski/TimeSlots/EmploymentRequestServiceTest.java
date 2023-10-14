package com.adamszablewski.TimeSlots;

import com.adamszablewski.messages.MessageSender;
import com.adamszablewski.model.Employee;
import com.adamszablewski.model.EmploymentRequest;
import com.adamszablewski.model.Facility;
import com.adamszablewski.repository.EmployeeRepository;
import com.adamszablewski.repository.EmploymentRequestRepository;
import com.adamszablewski.repository.FacilityRepository;
import com.adamszablewski.service.EmploymentRequestService;
import com.adamszablewski.util.helpers.UserTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@DataJpaTest(properties = "spring.config.name=application-test")
public class EmploymentRequestServiceTest {

    private EmploymentRequestService employmentRequestService;
    private MessageSender messageSender;
    private FacilityRepository facilityRepository;
    private EmploymentRequestRepository employmentRequestRepository;
    private EmployeeRepository employeeRepository;
    private UserTools userTools;

    @BeforeEach
    void setup(){
        employmentRequestService = new EmploymentRequestService(messageSender, facilityRepository,
                employmentRequestRepository, employeeRepository, userTools);
    }

    @Test
    void sendEmploymentRequestTest_shouldSendRequest(){
        Employee employee = Employee.builder()
                .id(1L)
                .build();
        Facility facility = Facility.builder()
                .id(1L)
                .build();
        EmploymentRequest employmentRequest = EmploymentRequest.builder()
                .id(1L)
                .status(false)
                .facility(facility)
                .employee(employee)
                .build();
        
    }
}
