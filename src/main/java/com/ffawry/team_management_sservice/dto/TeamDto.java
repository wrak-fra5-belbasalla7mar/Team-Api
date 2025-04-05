package com.ffawry.team_management_sservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private Long id;
    private String teamName;
    private Long managerId;
    private List<Long> memberIds;
}
