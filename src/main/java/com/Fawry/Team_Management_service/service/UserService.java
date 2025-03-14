package com.Fawry.Team_Management_service.service;

import com.Fawry.Team_Management_service.dto.UserDto;

import java.util.Optional;

public interface UserService {
    public Optional<UserDto> getUserById(Long userId) ;
}
