package com.adamszablewski.TimeSlots;

import com.adamszablewski.model.Appointment;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.model.Employee;
import com.adamszablewski.util.TimeSlotHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;



import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@DataJpaTest(properties = "spring.config.name=application-test")
public class TImeSlotHelperTest {
    TimeSlotHelper timeSlotHelper;
    @Mock
    AppointmentRepository appointmentRepository;
    @Mock
    UserServiceClient userServiceClient;

    @BeforeEach
    void setup(){
        timeSlotHelper = new TimeSlotHelper(appointmentRepository, userServiceClient);
    }

    @Test
    void isTimeSlotAvailable_should_return_true(){
        LocalTime taskStartTime = LocalTime.of(9, 0);
        LocalTime taskEndTime = LocalTime.of(11, 0);
        Employee employee = Employee.builder()
                .startTime(LocalTime.of(7, 0))
                .endTime(LocalTime.of(15, 0))
                .build();
        LocalDate date = LocalDate.now();
        Appointment appointment = Appointment.builder()
                .startTime(LocalTime.of(13, 0))
                .endTime(LocalTime.of(16, 0))
                .build();

        when(appointmentRepository.findByEmployeeAndDate(employee, date)).thenReturn(List.of(appointment));

        boolean result = timeSlotHelper.isTimeSlotAvailable(taskStartTime, taskEndTime, employee, date);

        assertThat(result).isTrue();

    }
    @Test
    void isTimeSlotAvailable_should_return_false_beforeWorkTime(){
        LocalTime taskStartTime = LocalTime.of(6, 0);
        LocalTime taskEndTime = LocalTime.of(8, 0);
        Employee employee = Employee.builder()
                .startTime(LocalTime.of(7, 0))
                .endTime(LocalTime.of(15, 0))
                .build();
        LocalDate date = LocalDate.now();
        Appointment appointment = Appointment.builder()
                .startTime(LocalTime.of(13, 0))
                .endTime(LocalTime.of(16, 0))
                .build();
        when(appointmentRepository.findByEmployeeAndDate(employee, date)).thenReturn(List.of(appointment));

        boolean result = timeSlotHelper.isTimeSlotAvailable(taskStartTime, taskEndTime, employee, date);

        assertThat(result).isFalse();

    }
    @Test
    void isTimeSlotAvailable_should_return_false_duringOtherAppointment(){
        LocalTime taskStartTime = LocalTime.of(12, 0);
        LocalTime taskEndTime = LocalTime.of(14, 0);
        Employee employee = Employee.builder()
                .startTime(LocalTime.of(7, 0))
                .endTime(LocalTime.of(15, 0))
                .build();
        LocalDate date = LocalDate.now();
        Appointment appointment = Appointment.builder()
                .startTime(LocalTime.of(13, 0))
                .endTime(LocalTime.of(16, 0))
                .build();
        when(appointmentRepository.findByEmployeeAndDate(employee, date)).thenReturn(List.of(appointment));

        boolean result = timeSlotHelper.isTimeSlotAvailable(taskStartTime, taskEndTime, employee, date);

        assertThat(result).isFalse();

    }

    @Test
    void doTimeRangesNotOverlap_should_return_false(){
        LocalTime taskStartTime = LocalTime.of(8, 0);
        LocalTime taskEndTime = LocalTime.of(11, 0);
        LocalTime existingAppointmentStart = LocalTime.of(12, 0);
        LocalTime existingAppointmentEnd = LocalTime.of(15, 0);

        boolean result = timeSlotHelper.timeRangesNotOverlapping(existingAppointmentStart, existingAppointmentEnd, taskStartTime, taskEndTime);

        assertThat(result).isTrue();
    }
    @Test
    void doTimeRangesNotOverlap_should_return_true(){
        LocalTime taskStartTime = LocalTime.of(9, 0);
        LocalTime taskEndTime = LocalTime.of(11, 0);
        LocalTime existingAppointmentStart = LocalTime.of(10, 0);
        LocalTime existingAppointmentEnd = LocalTime.of(13, 0);

        boolean result = timeSlotHelper.timeRangesNotOverlapping(existingAppointmentStart, existingAppointmentEnd, taskStartTime, taskEndTime);

        assertThat(result).isFalse();
    }
    @Test
    void doTimeRangesNotOverlap_should_return_true_2(){
        LocalTime taskStartTime = LocalTime.of(11, 0);
        LocalTime taskEndTime = LocalTime.of(14, 0);
        LocalTime existingAppointmentStart = LocalTime.of(10, 0);
        LocalTime existingAppointmentEnd = LocalTime.of(13, 0);

        boolean result = timeSlotHelper.timeRangesNotOverlapping(existingAppointmentStart, existingAppointmentEnd, taskStartTime, taskEndTime);

        assertThat(result).isFalse();
    }
    @Test
    void doTimeRangesNotOverlapTest_should_return_true_3(){
        LocalTime taskStartTime = LocalTime.of(11, 0);
        LocalTime taskEndTime = LocalTime.of(12, 0);
        LocalTime existingAppointmentStart = LocalTime.of(10, 0);
        LocalTime existingAppointmentEnd = LocalTime.of(13, 0);

        boolean result = timeSlotHelper.timeRangesNotOverlapping(existingAppointmentStart, existingAppointmentEnd, taskStartTime, taskEndTime);

        assertThat(result).isFalse();
    }

    @Test
    void isWithinEmployeeWorkHoursTest_should_return_true(){
        LocalTime taskStartTime = LocalTime.of(11, 0);
        LocalTime taskEndTime = LocalTime.of(14, 0);
        Employee employee = Employee.builder()
                .startTime(LocalTime.of(7, 0))
                .endTime(LocalTime.of(15, 0))
                .build();
        boolean result = timeSlotHelper.isWithinEmployeeWorkHours(taskStartTime, taskEndTime, employee);

        assertThat(result).isTrue();
    }
    @Test
    void isWithinEmployeeWorkHoursTest_should_return_false_after(){
        LocalTime taskStartTime = LocalTime.of(14, 0);
        LocalTime taskEndTime = LocalTime.of(16, 0);
        Employee employee = Employee.builder()
                .startTime(LocalTime.of(7, 0))
                .endTime(LocalTime.of(15, 0))
                .build();
        boolean result = timeSlotHelper.isWithinEmployeeWorkHours(taskStartTime, taskEndTime, employee);

        assertThat(result).isFalse();
    }
    @Test
    void isWithinEmployeeWorkHoursTest_should_return_false_before(){
        LocalTime taskStartTime = LocalTime.of(6, 0);
        LocalTime taskEndTime = LocalTime.of(9, 0);
        Employee employee = Employee.builder()
                .startTime(LocalTime.of(7, 0))
                .endTime(LocalTime.of(15, 0))
                .build();
        boolean result = timeSlotHelper.isWithinEmployeeWorkHours(taskStartTime, taskEndTime, employee);

        assertThat(result).isFalse();
    }

}
