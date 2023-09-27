package com.adamszablewski.dto.mapper;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.dto.*;
import com.adamszablewski.employmentRequests.EmploymentRequest;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.feignClients.classes.Client;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.tasks.Task;
import com.adamszablewski.util.Identifiable;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Mapper {

    public static  <T extends Identifiable> List<Long> convertObjectListToIdList(List<T> list){
        return list.stream()
                .map(Identifiable::getId)
                .collect(Collectors.toList());
    }
    public static <T extends Identifiable> Long convertObjectToId(T entity){
        return entity.getId();
    }

    public static AppoinmentDTO mapAppointmentToDto(Appointment appointment){
        return AppoinmentDTO.builder()
                .id(appointment.getId())
                .facility(mapFacilityToDto(appointment.getFacility()))
                .client(convertObjectToId(appointment.getClient()))
                .employee(convertObjectToId(appointment.getEmployee()))
                .task(mapTaskToDto(appointment.getTask()))
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .date(appointment.getDate())
                .build();
    }
    public static List<AppoinmentDTO> mapAppointmentToDto(List<Appointment> appointments){
        List<AppoinmentDTO> convertedList = new ArrayList<>();
        appointments.forEach(appointment -> {

            convertedList.add(AppoinmentDTO.builder()
                    .id(appointment.getId())
                    .facility(mapFacilityToDto(appointment.getFacility()))
                    .client(appointment.getClient().getId())
                    .employee(convertObjectToId(appointment.getEmployee()))
                    .task(mapTaskToDto(appointment.getTask()))
                    .startTime(appointment.getStartTime())
                    .endTime(appointment.getEndTime())
                    .date(appointment.getDate())
                    .build());

        });
        return convertedList;
    }
    public static FacilityDto mapFacilityToDto(Facility facility){
        return FacilityDto.builder()
                .id(facility.getId())
                .owner(convertObjectToId(facility.getOwner()))
                .name(facility.getName())
                .employees(convertObjectListToIdList(facility.getEmployees()))
                .city(facility.getCity())
                .country(facility.getCountry())
                .region(facility.getRegion())
                .tasks(mapTaskToDto(facility.getTasks()))
                .street(facility.getStreet())
                .houseNumber(facility.getHouseNumber())
                .build();
    }
    public static List<FacilityDto> mapFacilityToDto(List<Facility> facilities){
        List<FacilityDto> facilityDtos = new ArrayList<>();

        facilities.forEach(facility -> {
            facilityDtos.add(FacilityDto.builder()
                    .owner(convertObjectToId(facility.getOwner()))
                    .id(facility.getId())
                    .name(facility.getName())
                    .employees(convertObjectListToIdList(facility.getEmployees()))
                    .city(facility.getCity())
                    .country(facility.getCountry())
                    .region(facility.getRegion())
                    .tasks(mapTaskToDto(facility.getTasks()))
                    .street(facility.getStreet())
                    .houseNumber(facility.getHouseNumber())
                    .build());
        });
        return facilityDtos;
    }
    public static TaskDto mapTaskToDto(Task task){
        return TaskDto.builder()
                .id(task.getId())
                .category(task.getCategory())
                .city(task.getCity())
                .region(task.getRegion())
                .name(task.getName())
                .durationInMinutes(task.getDurationInMinutes())
                .employees(convertObjectListToIdList(task.getEmployees()))
                .facility(convertObjectToId(task.getFacility()))
                .price(task.getPrice())
                .currency(task.getCurrency())
                .build();

    }
    public static List<TaskDto> mapTaskToDto(List<Task> tasks){
        List<TaskDto> taskDtos = new ArrayList<>();

        tasks.forEach(task -> {
            taskDtos.add( TaskDto.builder()
                    .id(task.getId())
                    .category(task.getCategory())
                    .name(task.getName())
                    .durationInMinutes(task.getDurationInMinutes())
                    .employees(convertObjectListToIdList(task.getEmployees()))
                    .facility(convertObjectToId(task.getFacility()))
                    .price(task.getPrice())
                    .currency(task.getCurrency())
                    .build());
        });
        return taskDtos;

    }
    public static EmployeeDto mapEmployeeToDto(Employee employee){
        return EmployeeDto.builder()
                .id(employee.getId())
                .userId(convertObjectToId(employee.getUser()))
                .startTime(employee.getStartTime())
                .endTime(employee.getEndTime())
                .build();
    }
    public static List<EmployeeDto> mapEmployeeToDto(List<Employee> employees){
        List<EmployeeDto> employeeDtos = new ArrayList<>();

        employees.forEach(employee -> {
            employeeDtos.add(EmployeeDto.builder()
                    .id(employee.getId())
                    .userId(convertObjectToId(employee.getUser()))
                    .startTime(employee.getStartTime())
                    .endTime(employee.getEndTime())
                    .build());
        });
        return employeeDtos;
    }
    public static ClientDto mapClientToDto(Client client){
        return ClientDto.builder()
                .id(client.getId())
                .user(client.getUser().getId())
                .points(client.getPoints())
                .appointments(mapAppointmentToDto((client.getAppointments())))
                .build();
    }
    public static EmploymentRequestDTO mapEmploymentRequestToDTO(EmploymentRequest employmentRequest){
        return EmploymentRequestDTO.builder()
                .id(employmentRequest.getId())
                .status(employmentRequest.isStatus())
                .employee(mapEmployeeToDto(employmentRequest.getEmployee()))
                .facility(mapFacilityToDto(employmentRequest.getFacility()))
                .build();
    }
    public static List<EmploymentRequestDTO> mapEmploymentRequestToDTO(List<EmploymentRequest> employmentRequest){
        List<EmploymentRequestDTO> requestDTOS = new ArrayList<>();

        employmentRequest.forEach(request -> {
            requestDTOS.add(EmploymentRequestDTO.builder()
                    .id(request.getId())
                    .status(request.isStatus())
                    .employee(mapEmployeeToDto(request.getEmployee()))
                    .facility(mapFacilityToDto(request.getFacility()))
                    .build());
        });
        return requestDTOS;
    }

}
