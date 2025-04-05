package com.fawry.team_management_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private Long id;
    private String teamName;
    private Long managerId;
    private List<Long> memberIds;
}
