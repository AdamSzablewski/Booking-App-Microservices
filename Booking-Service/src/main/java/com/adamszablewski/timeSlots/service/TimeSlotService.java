package com.adamszablewski.timeSlots.service;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.exceptions.NoSuchTaskException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.exceptions.TimeSlotAlreadyTakenException;
import com.adamszablewski.feignClients.classes.Client;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.messages.MessageSender;
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
    private final MessageSender messageSender;



    public List<TimeSlot> getAvailableTimeSlotsForTaskAndDate(LocalDate date, Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(NoSuchTaskException::new);


        List<Employee> employeesForTask =  task.getEmployees();
        System.out.println("employees for task "+ employeesForTask);
        List<TimeSlot> availableTimeSlots = new ArrayList<>();

        employeesForTask.forEach(employee -> {
            LocalTime employeeStartTime = employee.getStartTime();
            LocalTime employeeEndTime = employee.getEndTime();
            while(!employeeStartTime.isAfter(employeeEndTime.minusMinutes(task.getDurationInMinutes()))){

                if(timeSlotHelper.isTimeSlotAvailable(employeeStartTime,
                        employeeStartTime.plusMinutes(task.getDurationInMinutes()), employee, date)){
                    availableTimeSlots.add(
                            TimeSlot.builder()
                                .startTime(employeeStartTime)
                                .endTime(employeeStartTime.plusMinutes(task.getDurationInMinutes()))
                                    .task(task)
                                    .date(date)
                                    .employee(employee)
                                    .facility(task.getFacility())
                                    .build()
                    );
                }
                employeeStartTime = employeeStartTime.plusMinutes(15);
            }
        });
        return availableTimeSlots;
    }


    public Appointment makeAppointmentFromTimeSlot(long id, TimeSlot timeSlot) {
        Client client = userServiceClient.getClientById(id)
                .orElseThrow(NoSuchUserException::new);
        System.out.println(timeSlot.toString());
        if(!timeSlotHelper.isTimeSlotAvailable(timeSlot.getStartTime(), timeSlot.getEndTime(),
                timeSlot.getEmployee(), timeSlot.getDate())){
                    throw new TimeSlotAlreadyTakenException();
        }

        Appointment appointment = Appointment.builder()
                .date(timeSlot.getDate())
                .startTime(timeSlot.getStartTime())
                .endTime(timeSlot.getEndTime())
                .employee(timeSlot.getEmployee())
                .task(timeSlot.getTask())
                .client(client)
                .facility(timeSlot.getFacility())
                .build();


        appointmentRepository.save(appointment);
        messageSender.sendAppointmentCreatedMessage(appointment);

        //rabbitMqProducer.sendMessage(appointment);
//        userRepository.save(client);
//        userRepository.save(employee);
        return appointment;
    }
}
