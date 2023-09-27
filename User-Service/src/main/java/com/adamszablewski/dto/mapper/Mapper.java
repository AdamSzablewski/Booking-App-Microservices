package com.adamszablewski.dto.mapper;

import com.adamszablewski.Identifiable;

import com.adamszablewski.dto.*;

import com.adamszablewski.feignClients.Appointment;
import com.adamszablewski.feignClients.Facility;
import com.adamszablewski.feignClients.Task;
import com.adamszablewski.users.UserClass;
import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.employee.Employee;
import com.adamszablewski.users.owners.Owner;
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
                .facility(appointment.getFacility().getId())
                .client(convertObjectToId(appointment.getClient()))
                .employee(mapEmployeeToDto(appointment.getEmployee()))
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
                .name(task.getName())
                .durationInMinutes(task.getDurationInMinutes())
                .employees(convertObjectListToIdList(task.getEmployees()))
                .facility(convertObjectToId(task.getFacility()))
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
