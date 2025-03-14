package com.Fawry.Team_Management_service.service.impl;

import com.Fawry.Team_Management_service.client.UserFeignClient;
import com.Fawry.Team_Management_service.dto.UserDto;
import com.Fawry.Team_Management_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserFeignClient userFeignClient;

    public Optional<UserDto> getUserById(Long userId) {
        return userFeignClient.getUserById(userId);
    }
}
