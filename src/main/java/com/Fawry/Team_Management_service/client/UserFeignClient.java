package com.Fawry.Team_Management_service.client;

import com.Fawry.Team_Management_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "user-service", url = "http://USER-SERVICE")
public interface UserFeignClient {
    @GetMapping("/api/users/{id}")
    Optional<UserDto> getUserById(@PathVariable Long id);
}