package com.adamszablewski.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentRequestDTO {

    private long id;
    private EmployeeDto employee;
    private FacilityDto facility;
    private boolean status;
}
