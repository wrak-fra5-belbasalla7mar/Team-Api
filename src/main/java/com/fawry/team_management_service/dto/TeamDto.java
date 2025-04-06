package com.Fawry.Team_Management_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private Long id;
    private String teamName;
    private Long managerId;
    private List<Long> memberIds;
}
