package com.adamszablewski.feign;

import com.adamszablewski.dto.LoginDto;
import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @PostMapping("/users/login")
    RestResponseDTO<Boolean> login(String email, String password);
}
