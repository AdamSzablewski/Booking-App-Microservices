package com.adamszablewski.service;

import com.adamszablewski.model.*;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.util.helpers.UserTools;
import com.adamszablewski.exceptions.NoSuchTaskException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.exceptions.TimeSlotAlreadyTakenException;
import com.adamszablewski.messages.MessageSender;
import com.adamszablewski.repository.FacilityRepository;
import com.adamszablewski.repository.TaskRepository;
import com.adamszablewski.util.helpers.TimeSlotHelper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static com.adamszablewski.dto.mapper.Mapper.*;


@Service
@AllArgsConstructor
public class TimeSlotService {

    private final TaskRepository taskRepository;
    private final TimeSlotHelper timeSlotHelper;
    private final AppointmentRepository appointmentRepository;
    private final MessageSender messageSender;
    private final UserTools userTools;
    private final FacilityRepository facilityRepository;






    public Set<TimeSlot> getAvailableTimeSlotsForTaskAndDate(LocalDate date, Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(NoSuchTaskException::new);


        Set<Employee> employeesForTask =  task.getEmployees();
        Set<TimeSlot> availableTimeSlots = new HashSet<>();

        employeesForTask.forEach(employee -> {
            LocalTime employeeStartTime = employee.getStartTime();
            LocalTime employeeEndTime = employee.getEndTime();
            while(!employeeStartTime.isAfter(employeeEndTime.minusMinutes(task.getDurationInMinutes()))){

                if(timeSlotHelper.isTimeSlotAvailable(employeeStartTime, employeeStartTime.plusMinutes(
                        task.getDurationInMinutes()), employee, date)){
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
        Client client = userTools.getClientById(id);
        Facility facility = facilityRepository.findById(timeSlot.getFacility().getId())
                .orElseThrow(NoSuchTaskException::new);
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
                .facility(facility)
                .client(client)
                .build();

        client.getAppointments().add(appointment);
        employee.getAppointments().add(appointment);
        task.getAppointments().add(appointment);
        appointmentRepository.save(appointment);

        messageSender.sendAppointmentCreatedMessage(appointment, appointment.getClient().getUser().getId());
        messageSender.sendAppointmentCreatedMessage(appointment, appointment.getEmployee().getUser().getId());


    }
}
