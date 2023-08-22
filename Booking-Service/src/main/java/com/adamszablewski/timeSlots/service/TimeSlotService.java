package com.adamszablewski.timeSlots.service;

import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.exceptions.NoSuchTaskException;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.tasks.repository.TaskRepository;
import com.adamszablewski.timeSlots.TimeSlot;
import com.adamszablewski.timeSlots.helper.TimeSlotHelper;
import com.adamszablewski.users.employee.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TimeSlotService {

    private final TaskRepository taskRepository;
    private final AppointmentRepository appointmentRepository;
    private final TimeSlotHelper timeSlotHelper;

    public List<TimeSlot> getAvailableTimeSlotsForTaskAndDate(LocalDate date, Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(NoSuchTaskException::new);

        List<Employee> employeesForTask = task.getEmployees();
        List<TimeSlot> avaiableTimeSlots = new ArrayList<>();

        employeesForTask.forEach(employee -> {
            LocalTime startTime = employee.getStartTime();
            LocalTime endTime = employee.getEndTime();
            while(startTime.isBefore(endTime.minusMinutes(task.getDurationInMinutes()))){
                if(timeSlotHelper.isTimeSlotAvailable(startTime, startTime.plusMinutes(task.getDurationInMinutes()), employee, date)){
                    avaiableTimeSlots.add(TimeSlot.builder()
                            .start(startTime)
                            .end(startTime.plusMinutes(task.getDurationInMinutes()))
                            .date(date).build());
                }
                startTime = startTime.plusMinutes(15);
            }
        });
        return avaiableTimeSlots;
    }


}
