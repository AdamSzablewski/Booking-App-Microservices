package com.adamszablewski.timeSlots.service;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.exceptions.NoSuchTaskException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.feignClients.classes.Client;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.rabbitMq.RabbitMqProducer;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.tasks.repository.TaskRepository;
import com.adamszablewski.timeSlots.TimeSlot;
import com.adamszablewski.timeSlots.helper.TimeSlotHelper;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TimeSlotService {

    private final TaskRepository taskRepository;

   // private final AppointmentRepository appointmentRepository;
    private final TimeSlotHelper timeSlotHelper;
    private final AppointmentRepository appointmentRepository;
    private final UserServiceClient userServiceClient;
    private final RabbitMqProducer rabbitMqProducer;

    public List<TimeSlot> getAvailableTimeSlotsForTaskAndDate(LocalDate date, Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(NoSuchTaskException::new);

        List<Employee> employeesForTask = task.getEmployees();
        List<TimeSlot> availableTimeSlots = new ArrayList<>();

        employeesForTask.forEach(employee -> {
            LocalTime employeeStartTime = employee.getStartTime();
            LocalTime employeeEndTime = employee.getEndTime();
            while(!employeeStartTime.isAfter(employeeEndTime.minusMinutes(task.getDurationInMinutes()))){

                if(timeSlotHelper.isTimeSlotAvailable(employeeStartTime,
                        employeeStartTime.plusMinutes(task.getDurationInMinutes()), employee, date)){
                    availableTimeSlots.add(TimeSlot.builder()
                            .start(employeeStartTime)
                            .end(employeeStartTime.plusMinutes(task.getDurationInMinutes()))
                            .build());
                }
                employeeStartTime = employeeStartTime.plusMinutes(15);
            }
        });
        return availableTimeSlots;
    }


    public ResponseEntity<String> makeAppointmentFromTimeSlot(long id, TimeSlot timeSlot) {
        Client client = userServiceClient.getClientById(id)
                .orElseThrow(NoSuchUserException::new);

        Employee employee = timeSlot.getEmployee();
        Appointment appointment = Appointment.builder()
                .date(timeSlot.getDate())
                .startTime(timeSlot.getStart())
                .endTime(timeSlot.getEnd())
                .employee(employee)
                .facility(employee.getWorkplace())
                .build();

        employee.getAppointments().add(appointment);
        appointmentRepository.save(appointment);
        userServiceClient.saveAllUsers(List.of(client, employee));
        //rabbitMqProducer.sendMessage(appointment);
//        userRepository.save(client);
//        userRepository.save(employee);
        return ResponseEntity.ok().build();
    }
}
