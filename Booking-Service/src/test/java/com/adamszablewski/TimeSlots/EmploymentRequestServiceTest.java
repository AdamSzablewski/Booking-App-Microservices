package com.adamszablewski.TimeSlots;

import com.adamszablewski.exceptions.NotAuthorizedException;
import com.adamszablewski.messages.MessageSender;
import com.adamszablewski.model.Employee;
import com.adamszablewski.model.EmploymentRequest;
import com.adamszablewski.model.Facility;
import com.adamszablewski.model.UserClass;
import com.adamszablewski.repository.EmployeeRepository;
import com.adamszablewski.repository.EmploymentRequestRepository;
import com.adamszablewski.repository.FacilityRepository;
import com.adamszablewski.service.EmploymentRequestService;
import com.adamszablewski.util.helpers.UserTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@DataJpaTest(properties = "spring.config.name=application-test")
public class EmploymentRequestServiceTest {

    private EmploymentRequestService employmentRequestService;
    @Mock
    private MessageSender messageSender;
    @Mock
    private FacilityRepository facilityRepository;
    @Mock
    private EmploymentRequestRepository employmentRequestRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private UserTools userTools;

    @BeforeEach
    void setup(){
        employmentRequestService = new EmploymentRequestService(messageSender, facilityRepository,
                employmentRequestRepository, employeeRepository, userTools);
    }

    @Test
    void sendEmploymentRequestTest_shouldSendRequest(){
        String email = "test@email.com";
        Employee employee = Employee.builder()
                .id(1L)
                .build();
        Facility facility = Facility.builder()
                .id(1L)
                .build();
        EmploymentRequest employmentRequest = EmploymentRequest.builder()
                .status(false)
                .facility(facility)
                .employee(employee)
                .build();
        when(userTools.getEmployeeByMail(email)).thenReturn(employee);
        when(facilityRepository.findById(facility.getId())).thenReturn(Optional.of(facility));

        employmentRequestService.sendEmploymentRequest(email, 1L);

        verify(employmentRequestRepository).save(eq(employmentRequest));
        verify(messageSender).sendEmploymentRequestMessage(employee, facility);
    }

    @Test
    void answerEmploymentRequestTest_should_acceptRequestAndSendMessage(){
        UserClass user = UserClass.builder()
                .id(1L)
                .email("test@email.com")
                .build();
        Employee employee = Employee.builder()
                .id(1L)
                .user(user)
                .build();

        Facility facility = Facility.builder()
                .id(1L)
                .employees(new HashSet<>())
                .build();
        EmploymentRequest employmentRequest = EmploymentRequest.builder()
                .id(1L)
                .status(false)
                .facility(facility)
                .employee(employee)
                .build();

        when(employmentRequestRepository.findById(employmentRequest.getId())).thenReturn(Optional.of(employmentRequest));

        employmentRequestService.answerEmploymentRequest(employmentRequest.getId(), true, "test@email.com");


        verify(messageSender).sendEmploymentRequestAccepted(employee, facility);
        verify(facilityRepository).save(facility);
        assertThat(facility.getEmployees().contains(employee)).isTrue();
    }
    @Test
    void answerEmploymentRequestTest_should_denyRequestAndSendMessage(){
        UserClass user = UserClass.builder()
                .id(1L)
                .email("test@email.com")
                .build();
        Employee employee = Employee.builder()
                .id(1L)
                .user(user)
                .build();

        Facility facility = Facility.builder()
                .id(1L)
                .employees(new HashSet<>())
                .build();
        EmploymentRequest employmentRequest = EmploymentRequest.builder()
                .id(1L)
                .status(false)
                .facility(facility)
                .employee(employee)
                .build();

        when(employmentRequestRepository.findById(employmentRequest.getId())).thenReturn(Optional.of(employmentRequest));

        employmentRequestService.answerEmploymentRequest(employmentRequest.getId(), false, "test@email.com");


        verify(messageSender).sendEmploymentRequestDenied(employee, facility);
        verify(facilityRepository, times(0)).save(facility);
        assertThat(facility.getEmployees().size()).isEqualTo(0);
        assertThat(employee.getWorkplace()).isEqualTo(null);
    }
    @Test
    void answerEmploymentRequestTest_should_throwUnathorizedException(){
        UserClass user = UserClass.builder()
                .id(1L)
                .email("test@email.com")
                .build();
        Employee employee = Employee.builder()
                .id(1L)
                .user(user)
                .build();

        Facility facility = Facility.builder()
                .id(1L)
                .employees(new HashSet<>())
                .build();
        EmploymentRequest employmentRequest = EmploymentRequest.builder()
                .id(1L)
                .status(false)
                .facility(facility)
                .employee(employee)
                .build();

        when(employmentRequestRepository.findById(employmentRequest.getId())).thenReturn(Optional.of(employmentRequest));

        assertThrows(NotAuthorizedException.class, ()-> {
            employmentRequestService.answerEmploymentRequest(employmentRequest.getId(), true, "notauthuser@mail.com");
        });

    }
}
