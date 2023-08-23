package com.adamszablewski.timeSlots.helper;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.users.employee.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
@Component
@AllArgsConstructor
public class TimeSlotHelper {

    AppointmentRepository appointmentRepository;
    public boolean isTimeSlotAvailable(LocalTime taskStartTime, LocalTime taskEndTime, Employee employee, LocalDate date) {
        List<Appointment> existingAppointments = appointmentRepository.findByEmployeeAndDate(employee, date);
        AtomicBoolean isAvailable = new AtomicBoolean(true);
        existingAppointments.forEach(appointment -> {
            if (doTimeRangesOverlap(appointment.getStartTime(), appointment.getEndTime(), taskStartTime, taskEndTime)) {
                isAvailable.set(false);

            }
            if (!isWithinEmployeeWorkHours(taskStartTime, taskEndTime, employee)) {
                isAvailable.set(false);
            }

        });
        return isAvailable.get();
    }
    public boolean doTimeRangesOverlap(LocalTime existingAppointmentStartTime, LocalTime existingAppointmentEndTime,
                                       LocalTime taskStartTime, LocalTime taskEndTime) {
        return taskStartTime.isBefore(existingAppointmentEndTime) && taskEndTime.isAfter(existingAppointmentStartTime);

    }
    public boolean isWithinEmployeeWorkHours(LocalTime taskStartTime, LocalTime taskEndTime, Employee employee) {
        LocalTime employeeStartTime = employee.getStartTime();
        LocalTime employeeEndTime = employee.getEndTime();

        return taskStartTime.isAfter(employeeStartTime) && taskEndTime.isBefore(employeeEndTime);
    }

}