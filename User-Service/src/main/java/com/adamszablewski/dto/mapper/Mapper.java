package com.adamszablewski.dto.mapper;

import com.adamszablewski.Identifiable;

import com.adamszablewski.dto.*;

import com.adamszablewski.feignClients.Appointment;
import com.adamszablewski.feignClients.Facility;
import com.adamszablewski.feignClients.Task;
import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.employee.Employee;
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
                .client(mapClientToDto(appointment.getClient()))
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
                    .facility(mapFacilityToDto(appointment.getFacility()))
                    .client(mapClientToDto(appointment.getClient()))
                    .employee(mapEmployeeToDto(appointment.getEmployee()))
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
                .name(facility.getName())
                .city(facility.getCity())
                .country(facility.getCountry())
                .region(facility.getRegion())
                .street(facility.getStreet())
                .houseNumber(facility.getHouseNumber())
                .build();
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
    public static ClientDto mapClientToDto(Client client){
        return ClientDto.builder()
                .id(client.getId())
                .user(client.getUser().getId())
                .points(client.getPoints())
                .appointments(mapAppointmentToDto(client.getAppointments()))
                .build();
    }

}