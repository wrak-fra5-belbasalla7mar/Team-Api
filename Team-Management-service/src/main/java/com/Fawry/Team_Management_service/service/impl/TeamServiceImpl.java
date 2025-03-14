package com.Fawry.Team_Management_service.service.impl;

import com.Fawry.Team_Management_service.client.UserFeignClient;
import com.Fawry.Team_Management_service.dal.model.Team;
import com.Fawry.Team_Management_service.dal.model.TeamMember;
import com.Fawry.Team_Management_service.dal.repo.TeamRepository;
import com.Fawry.Team_Management_service.dto.TeamDto;
import com.Fawry.Team_Management_service.dto.UserDto;
import com.Fawry.Team_Management_service.exception.NotFoundException;
import com.Fawry.Team_Management_service.mapper.TeamMapper;
import com.Fawry.Team_Management_service.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;

    private UserFeignClient userClient;

    private RabbitTemplate rabbitTemplate;

    private TeamMapper teamMapper;

    @Override
    public List<TeamDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TeamDto createTeam(TeamDto teamDto) {
        Team team = teamMapper.toEntity(teamDto);
        team = teamRepository.save(team);
        return teamMapper.toDto(team);
    }
    @Override
    public TeamDto getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + id));
        return teamMapper.toDto(team);
    }
    @Override
    public void addMember(Long teamId, Long userId) {
        Optional<UserDto> optionalUser = userClient.getUserById(userId);
        Optional<Team> optionalTeam = teamRepository.findById(teamId);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found with id: " + userId);
        }
        if (optionalTeam.isEmpty()) {
            throw new NotFoundException("Team not found with id: " + teamId);
        }

        Team team = optionalTeam.get();
        UserDto user = optionalUser.get();

        TeamMember member = new TeamMember();
        member.setUserId(user.getId());
        member.setTeam(team);

        team.getMembers().add(member);
        teamRepository.save(team);

        rabbitTemplate.convertAndSend("teamExchange", "team.addMember", userId);
    }

}
