package com.adamszablewski.util;

import com.adamszablewski.model.Appointment;
import com.adamszablewski.repository.AppointmentRepository;

import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.model.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
@Component
@AllArgsConstructor
public class TimeSlotHelper {

    AppointmentRepository appointmentRepository;
    UserServiceClient userServiceClient;
    public boolean isTimeSlotAvailable(LocalTime taskStartTime, LocalTime taskEndTime, Employee employee, LocalDate date) {

        List<Appointment> existingAppointments = appointmentRepository.findByEmployeeAndDate(employee, date);
        AtomicBoolean isAvailable = new AtomicBoolean(true);
        existingAppointments.forEach(appointment -> {

            if (!timeRangesNotOverlapping(appointment.getStartTime(), appointment.getEndTime(), taskStartTime, taskEndTime)) {
                isAvailable.set(false);

            }
            if (!isWithinEmployeeWorkHours(taskStartTime, taskEndTime, employee)) {
                isAvailable.set(false);
            }

        });
        return isAvailable.get();
    }
    public boolean timeRangesNotOverlapping(LocalTime existingAppointmentStartTime, LocalTime existingAppointmentEndTime,
                                            LocalTime taskStartTime, LocalTime taskEndTime) {
        //return taskStartTime.isBefore(existingAppointmentEndTime) && taskEndTime.isAfter(existingAppointmentStartTime);
        return (taskStartTime.isBefore(existingAppointmentStartTime) && taskEndTime.isBefore(existingAppointmentStartTime)) ||
                (taskStartTime.isAfter(existingAppointmentEndTime) && taskEndTime.isAfter(existingAppointmentEndTime));


    }
    public boolean isWithinEmployeeWorkHours(LocalTime taskStartTime, LocalTime taskEndTime, Employee employee) {
        LocalTime employeeStartTime = employee.getStartTime();
        LocalTime employeeEndTime = employee.getEndTime();

        return taskStartTime.isAfter(employeeStartTime) && taskEndTime.isBefore(employeeEndTime);
    }




}
