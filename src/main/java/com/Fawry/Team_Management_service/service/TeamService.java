package com.Fawry.Team_Management_service.service;

import com.Fawry.Team_Management_service.dto.TeamDto;

import java.util.List;

public interface TeamService {
    public TeamDto getTeamById(Long teamId);

    public List<TeamDto> getAllTeams();

    public TeamDto createTeam(TeamDto teamDto);

    public void addMember(Long teamId, Long userId);
}
