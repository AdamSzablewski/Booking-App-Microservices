package com.adamszablewski.TimeSlots;

import com.adamszablewski.dto.EmployeeDto;
import com.adamszablewski.dto.FacilityDto;
import com.adamszablewski.dto.TaskDto;
import com.adamszablewski.dto.mapper.Mapper;
import com.adamszablewski.helpers.UserTools;
import com.adamszablewski.model.*;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.messages.MessageSender;
import com.adamszablewski.repository.ClientRepository;
import com.adamszablewski.repository.FacilityRepository;
import com.adamszablewski.repository.TaskRepository;
import com.adamszablewski.helpers.TimeSlotHelper;
import com.adamszablewski.service.TimeSlotService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@DataJpaTest(properties = "spring.config.name=application-test")
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
    @Mock
    MessageSender messageSender;
    @Mock
    UserTools userTools;
    @Mock
    ClientRepository clientRepository;
    @Mock
    FacilityRepository facilityRepository;

    Mapper mapper;


    @BeforeEach
    void setup(){
        timeSlotService = new TimeSlotService(taskRepository,  timeSlotHelper,
                appointmentRepository, messageSender, userTools, facilityRepository, mapper);
    }

    @Test
    void getAvailableTimeSlotsForTaskAndDate_should_return_3_for_emptyDay(){
        LocalDate date = LocalDate.now();

        Task task = Task.builder()
                .id(0L)
                .durationInMinutes(180)
                .build();

        Facility facility = Facility.builder()
                .id(0L)
                .tasks(Set.of(task))
                .build();
        UserClass user = UserClass.builder()
                .id(0L)
                .build();
        Owner owner = Owner.builder()
                .id(0L)
                .user(user)
                .facilities(Set.of(facility))
                .build();
        Employee employee = Employee.builder()
                .id(1L)
                .startTime(LocalTime.of(7, 0))
                .endTime(LocalTime.of(16, 0))
                .tasks(Set.of(task))
                .user(user)
                .appointments(new HashSet<>())
                .build();
        user.setOwner(owner);
        facility.setOwner(owner);
        task.setFacility(facility);
        task.setEmployees(Set.of(employee));



        TimeSlot ts1 = TimeSlot.builder()
                .emloyee(mapper.mapEmployeeToDto(employee))
                .task(mapper.mapTaskToDto(task))
                .facility(mapper.mapFacilityToDto(facility))
                .startTime(LocalTime.of(7, 0))
                .endTime(LocalTime.of(10, 0))
                .date(LocalDate.now())
                .build();
        TimeSlot ts2 = TimeSlot.builder()
                .emloyee(mapper.mapEmployeeToDto(employee))
                .task(mapper.mapTaskToDto(task))
                .facility(mapper.mapFacilityToDto(facility))
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(13, 0))
                .date(LocalDate.now())
                .build();
        TimeSlot ts3 = TimeSlot.builder()
                .emloyee(mapper.mapEmployeeToDto(employee))
                .task(mapper.mapTaskToDto(task))
                .facility(mapper.mapFacilityToDto(facility))
                .startTime(LocalTime.of(13, 0))
                .endTime(LocalTime.of(16, 0))
                .date(LocalDate.now())
                .build();



        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        //when(userServiceClient.findEmployeesForIds(task.getEmployees())).thenReturn(Set.of(employee));

        AtomicReference<LocalTime> lastAppointmentEndTime = new AtomicReference<>(employee.getStartTime());

        when(timeSlotHelper.isTimeSlotAvailable(any(LocalTime.class), any(LocalTime.class), any(Employee.class),
                any(LocalDate.class))).thenAnswer(invocation -> {
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
        Set<TimeSlot> timeSlots = timeSlotService.getAvailableTimeSlotsForTaskAndDate(date, task.getId());

        assertThat(timeSlots).contains(ts1, ts2, ts3);
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
                .tasks(Set.of(task))
                .appointments(new HashSet<>())
                .build();

        task.setEmployees(Set.of(employee));

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));


        lenient().when(timeSlotHelper.isTimeSlotAvailable(any(LocalTime.class), any(LocalTime.class), any(Employee.class),
                any(LocalDate.class))).thenAnswer(invocation -> false);

        Set<TimeSlot> timeSlots = timeSlotService.getAvailableTimeSlotsForTaskAndDate(date, task.getId());

        assertThat(timeSlots.size()).isEqualTo(0);

    }

//    @Test
//    void makeAppointmentFromTimeSlot(){
//        EmployeeDto employee = EmployeeDto.builder()
//                .id(1L)
//                .build();
//        TimeSlot timeSlot = TimeSlot.builder()
//                .emloyee(employee)
//                .startTime(LocalTime.of(10, 30))
//                .endTime(LocalTime.of(11, 30))
//                .facility(
//                        FacilityDto.builder()
//                                .city("Gdansk")
//                                .country("Poland")
//                                .region("Pomorskie")
//                                .houseNumber("19")
//                                .name("Test Barber")
//                                .build())
//                .date(LocalDate.now())
//                .build();
//        Client client = Client.builder()
//                .id(1L)
//                .build();
//        Appointment appointment = Appointment.builder()
//                .date(timeSlot.getDate())
//                .startTime(timeSlot.getStartTime())
//                .endTime(timeSlot.getEndTime())
//                .client(client)
//                .facility(timeSlot.getFacility())
//                .employee(timeSlot.getEmployee())
//                .build();
//
//        when(userServiceClient.getClientById(client.getId())).thenReturn(Optional.of(client));
//        when(timeSlotHelper.isTimeSlotAvailable(timeSlot.getStartTime(), timeSlot.getEndTime(),
//                timeSlot.getEmployee(), timeSlot.getDate())).thenReturn(true);
//        Appointment response = timeSlotService.makeAppointmentFromTimeSlot(1L, timeSlot);
//
//
//        verify(appointmentRepository).save(eq(appointment));
//        assertThat(response).isEqualTo(appointment);
//
//
//    }

}
