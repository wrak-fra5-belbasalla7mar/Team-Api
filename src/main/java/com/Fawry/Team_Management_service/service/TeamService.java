package com.Fawry.Team_Management_service.service;

import com.Fawry.Team_Management_service.dal.model.Team;
import com.Fawry.Team_Management_service.dto.TeamDto;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    public TeamDto getTeamById(Long teamId);

    public List<TeamDto> getAllTeams();

    public TeamDto createTeam(TeamDto teamDto);

    public void addMember(Long teamId, Long userId);

    public TeamDto assignManager(Long teamId, Long managerId);

    public void removeMember(Long teamId, Long userId);
    public TeamDto getTeamByManagerId(Long managerId) ;

}
