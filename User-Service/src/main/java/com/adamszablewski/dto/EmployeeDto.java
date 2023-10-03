package com.adamszablewski.dto;

import com.adamszablewski.model.Facility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDto {

    private long id;
    private long userId;
    private LocalTime startTime;
    private LocalTime endTime;
    private FacilityDto workplace;

}
