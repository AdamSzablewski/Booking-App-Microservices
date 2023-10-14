package com.adamszablewski.TimeSlots;

import com.adamszablewski.messages.MessageSender;
import com.adamszablewski.model.Appointment;
import com.adamszablewski.model.Employee;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.repository.EmployeeRepository;
import com.adamszablewski.util.helpers.AppointmentHelper;
import com.adamszablewski.util.helpers.UserTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@DataJpaTest(properties = "spring.config.name=application-test")
public class AppointmentHelperTest {

    private AppointmentHelper appointmentHelper;

    @Mock
    AppointmentRepository appointmentRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    MessageSender messageSender;
    @Mock
    UserTools userTools;

    @BeforeEach
    void setup(){
        appointmentHelper = new AppointmentHelper(appointmentRepository, employeeRepository, messageSender, userTools);
    }

    @Test
    void changeEmployeeForAppointmentTest_shouldChangeEmployeesForAppointment(){
        String newEmployeeEmail = "newEmp@mail.com";
        Appointment appointment = Appointment.builder()
                .id(1L)
                .build();
        Employee oldEmployee = Employee.builder()
                .id(1L)
                .appointments(new HashSet<>())
                .build();
        Employee newEmployee = Employee.builder()
                .id(1L)
                .appointments(new HashSet<>())
                .build();
        appointment.setEmployee(oldEmployee);
        oldEmployee.getAppointments().add(appointment);
        when(userTools.getEmployeeByMail(newEmployeeEmail)).thenReturn(newEmployee);

        appointmentHelper.changeEmployeeForAppointment(appointment, oldEmployee, newEmployeeEmail);

        assertThat(oldEmployee.getAppointments().size()).isEqualTo(0);
        assertThat(newEmployee.getAppointments().size()).isEqualTo(1);
        assertThat(appointment.getEmployee()).isEqualTo(newEmployee);

    }

}
