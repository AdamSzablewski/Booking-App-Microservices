package com.adamszablewski.timeSlots.service;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.dto.ClientDto;
import com.adamszablewski.exceptions.NoSuchTaskException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.exceptions.TimeSlotAlreadyTakenException;
import com.adamszablewski.feignClients.classes.Client;
import com.adamszablewski.feignClients.classes.ClientRepository;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.messages.MessageSender;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.tasks.repository.TaskRepository;
import com.adamszablewski.timeSlots.TimeSlot;
import com.adamszablewski.timeSlots.helper.TimeSlotHelper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.adamszablewski.dto.mapper.Mapper.*;

@Service
@AllArgsConstructor
public class TimeSlotService {

    private final TaskRepository taskRepository;
    private final TimeSlotHelper timeSlotHelper;
    private final AppointmentRepository appointmentRepository;
    private final UserServiceClient userServiceClient;
    private final MessageSender messageSender;
    private final ClientRepository clientRepository;





    public List<TimeSlot> getAvailableTimeSlotsForTaskAndDate(LocalDate date, Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(NoSuchTaskException::new);


        List<Employee> employeesForTask =  task.getEmployees();
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
                                .date(date)
                                .task(mapTaskToDto(task))
                                .endTime(employeeStartTime.plusMinutes(task.getDurationInMinutes()))
                                .emloyee(mapEmployeeToDto(employee))
                                .facility(mapFacilityToDto(task.getFacility()))
                                .build()
                    );
                }
                employeeStartTime = employeeStartTime.plusMinutes(15);
            }
        });
        return availableTimeSlots;
    }


    public void makeAppointmentFromTimeSlot(long id, TimeSlot timeSlot) {
        System.out.println("timeSlot");

        Client client = clientRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);
        Task task = taskRepository.findById(timeSlot.getTask().getId())
                .orElseThrow(NoSuchTaskException::new);
        Employee employee = task.getEmployees()
                .stream()
                .filter(emp -> emp.getId() == timeSlot.getEmloyee().getId())
                .findFirst()
                .orElseThrow(NoSuchUserException::new);

        if(!timeSlotHelper.isTimeSlotAvailable(timeSlot.getStartTime(), timeSlot.getEndTime(),
                employee, timeSlot.getDate())){
                    throw new TimeSlotAlreadyTakenException();
        }

        Appointment appointment = Appointment.builder()
                .date(timeSlot.getDate())
                .startTime(timeSlot.getStartTime())
                .endTime(timeSlot.getEndTime())
                .employee(employee)
                .task(task)
                .client(client)
                .facility(task.getFacility())
                .build();

        client.getAppointments().add(appointment);
        employee.getAppointments().add(appointment);
        appointmentRepository.save(appointment);

        messageSender.sendAppointmentCreatedMessage(appointment);

//        rabbitMqProducer.sendMessage(appointment);
//        userRepository.save(client);
//        userRepository.save(employee);

    }
}
