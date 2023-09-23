package com.adamszablewski.dto;
import lombok.*;

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

}
