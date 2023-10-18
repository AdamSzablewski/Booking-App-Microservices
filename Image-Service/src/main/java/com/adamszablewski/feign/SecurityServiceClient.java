package com.adamszablewski.feign;


import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "SECURITY-SERVICE")
public interface SecurityServiceClient {
    @GetMapping("/validate/owner/facilityID/{facilityId}/user/{userEmail}")
    RestResponseDTO<Boolean> isOwner(@PathVariable long facilityId,@PathVariable String userEmail);

    @GetMapping("/validate/employee/facilityID/{facilityId}/user/{userEmail}")
    RestResponseDTO<Boolean> isEmployee(@PathVariable long facilityId,@PathVariable String userEmail);
}
