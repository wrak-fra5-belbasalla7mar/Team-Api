package com.ffawry.team_management_sservice.service;

import com.ffawry.team_management_sservice.dto.TeamDto;

import java.util.List;

public interface TeamService {
    public TeamDto getTeamById(Long teamId);

    public List<TeamDto> getAllTeams();

    public TeamDto createTeam(TeamDto teamDto);

    public void addMember(Long teamId, Long userId);

    public TeamDto assignManager(Long teamId, Long managerId);

    public void removeMember(Long teamId, Long userId);
    public List<TeamDto> getTeamByManagerId(Long managerId) ;

}
