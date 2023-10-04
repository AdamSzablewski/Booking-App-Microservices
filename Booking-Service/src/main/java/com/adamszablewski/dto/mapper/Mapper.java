package com.adamszablewski.dto.mapper;

import com.adamszablewski.model.Appointment;
import com.adamszablewski.dto.*;
import com.adamszablewski.model.EmploymentRequest;
import com.adamszablewski.model.Facility;
import com.adamszablewski.model.Client;
import com.adamszablewski.model.Employee;
import com.adamszablewski.model.Task;
import com.adamszablewski.util.Identifiable;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Mapper {

    public static  <T extends Identifiable> Set<Long> convertObjectListToIdList(Set<T> set){
        if (set == null){
            return Collections.emptySet();
        }
        return set.stream()
                .map(Identifiable::getId)
                .collect(Collectors.toSet());
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
    public static Set<AppoinmentDTO> mapAppointmentToDto(Set<Appointment> appointments){
        Set<AppoinmentDTO> convertedList = new HashSet<>();
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
    public static Set<FacilityDto> mapFacilityToDto(Set<Facility> facilities){
        Set<FacilityDto> facilityDtos = new HashSet<>();

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
    public static Set<FacilityDto> mapFacilityToDto(Set<Facility> facilities, boolean isLimited){
        Set<FacilityDto> facilityDtos = new HashSet<>();

        facilities.forEach(facility -> {
            facilityDtos.add(FacilityDto.builder()
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
                .appointments(convertObjectListToIdList(task.getAppointments()))
                .durationInMinutes(task.getDurationInMinutes())
                .employees(convertObjectListToIdList(task.getEmployees()))
                .facility(convertObjectToId(task.getFacility()))
                .price(task.getPrice())
                .currency(task.getCurrency())
                .build();
    }
    public static Set<TaskDto> mapTaskToDto(Set<Task> tasks){
        Set<TaskDto> taskDtos = new HashSet<>();

        tasks.forEach(task -> {
            taskDtos.add( TaskDto.builder()
                    .id(task.getId())
                    .category(task.getCategory())
                    .name(task.getName())
                    .durationInMinutes(task.getDurationInMinutes())
                    .employees(convertObjectListToIdList(task.getEmployees()))
                    .facility(convertObjectToId(task.getFacility()))
                    .price(task.getPrice())
                    .appointments(convertObjectListToIdList(task.getAppointments()))
                    .currency(task.getCurrency())
                    .build());
        });
        return taskDtos;

    }
    public static Set<TaskDto> mapTaskToDto(Set<Task> tasks, boolean isLimited){
        Set<TaskDto> taskDtos = new HashSet<>();

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
    public static Set<EmployeeDto> mapEmployeeToDto(Set<Employee> employees){
        Set<EmployeeDto> employeeDtos = new HashSet<>();

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
    public static Set<EmploymentRequestDTO> mapEmploymentRequestToDTO(Set<EmploymentRequest> employmentRequest){
        Set<EmploymentRequestDTO> requestDTOS = new HashSet<>();

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
