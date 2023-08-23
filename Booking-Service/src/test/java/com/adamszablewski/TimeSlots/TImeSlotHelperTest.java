package com.adamszablewski.TimeSlots;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.tasks.repository.TaskRepository;
import com.adamszablewski.timeSlots.helper.TimeSlotHelper;
import com.adamszablewski.timeSlots.service.TimeSlotService;
import com.adamszablewski.users.employee.Employee;
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
@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
public class TImeSlotHelperTest {
    TimeSlotHelper timeSlotHelper;



    @Mock
    AppointmentRepository appointmentRepository;

    @BeforeEach
    void setup(){
        timeSlotHelper = new TimeSlotHelper(appointmentRepository);
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
    void doTimeRangesOverlap_should_return_false(){
        LocalTime taskStartTime = LocalTime.of(8, 0);
        LocalTime taskEndTime = LocalTime.of(11, 0);
        LocalTime existingAppointmentStart = LocalTime.of(12, 0);
        LocalTime existingAppointmentEnd = LocalTime.of(15, 0);

        boolean result = timeSlotHelper.doTimeRangesOverlap(existingAppointmentStart, existingAppointmentEnd, taskStartTime, taskEndTime);

        assertThat(result).isFalse();
    }
    @Test
    void doTimeRangesOverlap_should_return_true(){
        LocalTime taskStartTime = LocalTime.of(9, 0);
        LocalTime taskEndTime = LocalTime.of(11, 0);
        LocalTime existingAppointmentStart = LocalTime.of(10, 0);
        LocalTime existingAppointmentEnd = LocalTime.of(13, 0);

        boolean result = timeSlotHelper.doTimeRangesOverlap(existingAppointmentStart, existingAppointmentEnd, taskStartTime, taskEndTime);

        assertThat(result).isTrue();
    }
    @Test
    void doTimeRangesOverlapTest_should_return_true_2(){
        LocalTime taskStartTime = LocalTime.of(11, 0);
        LocalTime taskEndTime = LocalTime.of(14, 0);
        LocalTime existingAppointmentStart = LocalTime.of(10, 0);
        LocalTime existingAppointmentEnd = LocalTime.of(13, 0);

        boolean result = timeSlotHelper.doTimeRangesOverlap(existingAppointmentStart, existingAppointmentEnd, taskStartTime, taskEndTime);

        assertThat(result).isTrue();
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
