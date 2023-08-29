package com.adamszablewski.TimeSlots;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.tasks.repository.TaskRepository;
import com.adamszablewski.timeSlots.TimeSlot;
import com.adamszablewski.timeSlots.helper.TimeSlotHelper;
import com.adamszablewski.timeSlots.service.TimeSlotService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
public class TimeSlotServiceTest {
    TimeSlotService timeSlotService;



    @Mock
    TaskRepository taskRepository;

    @Mock
    TimeSlotHelper timeSlotHelper;

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    UserServiceClient userServiceClient;

    @BeforeEach
    void setup(){
        timeSlotService = new TimeSlotService(taskRepository,  timeSlotHelper, appointmentRepository, userServiceClient);
    }

    @Test
    void getAvailableTimeSlotsForTaskAndDate_should_return_3_for_emptyDay(){
        LocalDate date = LocalDate.now();
        Task task = Task.builder()
                .id(0L)
                .durationInMinutes(180)
                .build();
        Employee employee = Employee.builder()
                .startTime(LocalTime.of(7, 0))
                .endTime(LocalTime.of(16, 0))
                .services(List.of(task))
                .appointments(new ArrayList<>())
                .build();
        TimeSlot ts1 = TimeSlot.builder()
                .start(LocalTime.of(7, 0))
                .end(LocalTime.of(10, 0))
                .build();
        TimeSlot ts2 = TimeSlot.builder()
                .start(LocalTime.of(10, 0))
                .end(LocalTime.of(13, 0))
                .build();
        TimeSlot ts3 = TimeSlot.builder()
                .start(LocalTime.of(13, 0))
                .end(LocalTime.of(16, 0))
                .build();
        task.setEmployees(List.of(employee));

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        AtomicReference<LocalTime> lastAppointmentEndTime = new AtomicReference<>(employee.getStartTime());

        when(timeSlotHelper.isTimeSlotAvailable(any(), any(), any(), any())).thenAnswer(invocation -> {
            LocalTime startTime = invocation.getArgument(0);

            boolean isAvailbale =  !startTime.isBefore(lastAppointmentEndTime.get());
//            System.out.println(" ");
//            System.out.println("---------------------------------");
//            System.out.println("Start Time: " + startTime);
//            System.out.println("Three Hours After Last Appointment: " + lastAppointmentEndTime.get());
//            System.out.println("Is Available: " + isAvailbale);
            if (isAvailbale){
                lastAppointmentEndTime.set(startTime.plusMinutes(task.getDurationInMinutes()));
            }
            return isAvailbale;
        });
        List<TimeSlot> timeSlots = timeSlotService.getAvailableTimeSlotsForTaskAndDate(date, task.getId());

        assertThat(timeSlots.size()).isEqualTo(3);
        assertThat(timeSlots.get(0)).isEqualTo(ts1);
        assertThat(timeSlots.get(1)).isEqualTo(ts2);
        assertThat(timeSlots.get(2)).isEqualTo(ts3);
    }
    @Test
    void getAvailableTimeSlotsForTaskAndDate_should_return_0(){
        LocalDate date = LocalDate.now();
        Task task = Task.builder()
                .id(0L)
                .durationInMinutes(180)
                .build();
        Employee employee = Employee.builder()
                .startTime(LocalTime.of(7, 0))
                .endTime(LocalTime.of(16, 0))
                .services(List.of(task))
                .appointments(new ArrayList<>())
                .build();

        task.setEmployees(List.of(employee));

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        AtomicReference<LocalTime> lastAppointmentEndTime = new AtomicReference<>(employee.getStartTime());

        when(timeSlotHelper.isTimeSlotAvailable(any(), any(), any(), any())).thenReturn(false);

        List<TimeSlot> timeSlots = timeSlotService.getAvailableTimeSlotsForTaskAndDate(date, task.getId());

        assertThat(timeSlots.size()).isEqualTo(0);

    }

}
