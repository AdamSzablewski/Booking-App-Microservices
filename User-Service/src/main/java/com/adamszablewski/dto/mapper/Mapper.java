package com.adamszablewski.dto.mapper;

import com.adamszablewski.Identifiable;

import com.adamszablewski.dto.*;

import com.adamszablewski.model.Appointment;
import com.adamszablewski.model.Facility;
import com.adamszablewski.model.Task;
import com.adamszablewski.model.UserClass;
import com.adamszablewski.model.Client;
import com.adamszablewski.model.Employee;
import com.adamszablewski.model.Owner;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Mapper {

    public static  <T extends Identifiable> Set<Long> convertObjectListToIdList(Set<T> list){
        return list.stream()
                .map(Identifiable::getId)
                .collect(Collectors.toSet());
    }
    public static <T extends Identifiable> Long convertObjectToId(T entity){
        return entity.getId();
    }

    public static AppoinmentDTO mapAppointmentToDto(Appointment appointment){
        return AppoinmentDTO.builder()
                .id(appointment.getId())
                .facility(appointment.getFacility().getId())
                .client(convertObjectToId(appointment.getClient()))
                .employee(mapEmployeeToDto(appointment.getEmployee()))
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
                    .facility(appointment.getFacility().getId())
                    .client(convertObjectToId(appointment.getClient()))
                    .employee(mapEmployeeToDto(appointment.getEmployee()))
                    .task(mapTaskToDto(appointment.getTask()))
                    .startTime(appointment.getStartTime())
                    .endTime(appointment.getEndTime())
                    .date(appointment.getDate())
                    .build());

        });
        return convertedList;
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
                    .currency(task.getCurrency())
                    .build());
        });
        return taskDtos;

    }
    public static FacilityDto mapFacilityToDto(Facility facility){
        return FacilityDto.builder()
                .id(facility.getId())
                .name(facility.getName())
                .city(facility.getCity())
                .country(facility.getCountry())
                .region(facility.getRegion())
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
    public static TaskDto mapTaskToDto(Task task){
        return TaskDto.builder()
                .id(task.getId())
                .category(task.getCategory())
                .name(task.getName())
                .durationInMinutes(task.getDurationInMinutes())
                .employees(convertObjectListToIdList(task.getEmployees()))
                .facility(convertObjectToId(task.getFacility()))
                .appointments(convertObjectListToIdList(task.getAppointments()))
                .price(task.getPrice())
                .currency(task.getCurrency())
                .build();

    }

    public static EmployeeDto mapEmployeeToDto(Employee employee){
        return EmployeeDto.builder()
                .id(employee.getId())
                .userId(convertObjectToId(employee.getUser()))
                .startTime(employee.getStartTime())
                .endTime(employee.getEndTime())
                .workplace(mapFacilityToDto(employee.getWorkplace()))
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
    public static OwnerDto mapOwnerToDto(Owner owner){
        return OwnerDto.builder()
                .id(owner.getId())
                .user(owner.getUser().getId())
                .facilities(mapFacilityToDto(owner.getFacilities()))
                .build();
    }
    public static UserClassDTO mapUserToDto(UserClass user){
        return UserClassDTO.builder()
                .id(user.getId())
                .owner(user.getOwner() == null ? null : mapOwnerToDto(user.getOwner()))
                .employee(user.getEmployee() == null ? null : mapEmployeeToDto(user.getEmployee()))
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .city(user.getCity())
                .region(user.getRegion())
                .country(user.getCountry())
                .client(user.getClient() == null ? null : mapClientToDto(user.getClient()))
                .build();
    }

}
