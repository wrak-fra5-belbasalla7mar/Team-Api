package com.Fawry.Team_Management_service.service.impl;

import com.Fawry.Team_Management_service.dal.model.Team;
import com.Fawry.Team_Management_service.dal.model.TeamMember;
import com.Fawry.Team_Management_service.dal.repo.TeamMemberRepository;
import com.Fawry.Team_Management_service.dal.repo.TeamRepository;
import com.Fawry.Team_Management_service.dto.TeamDto;
import com.Fawry.Team_Management_service.dto.UserDto;
import com.Fawry.Team_Management_service.exception.NotFoundException;
import com.Fawry.Team_Management_service.mapper.TeamMapper;
import com.Fawry.Team_Management_service.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

    private static final ParameterizedTypeReference<UserDto> USER_DTO_TYPE = new ParameterizedTypeReference<>() {
    };

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamMapper teamMapper;
    private final WebClient webClient = WebClient.create("http://localhost:8080/manager"); // Directly create WebClient

    @Override
    public List<TeamDto> getAllTeams() {
        logger.info("Fetching all teams");
        return teamRepository.findAll().stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TeamDto createTeam(TeamDto teamDto) {
        logger.info("Creating team with data: {}", teamDto);
        Team team = teamMapper.toEntity(teamDto);
        team = teamRepository.save(team);
        logger.info("Team created with id: {}", team.getId());
        return teamMapper.toDto(team);
    }

    @Override
    public TeamDto getTeamById(Long id) {
        logger.info("Fetching team with id: {}", id);
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + id));
        return teamMapper.toDto(team);
    }

    @Override
    public TeamDto getTeamByManagerId(Long managerId) {
        logger.info("Fetching team managed by user id: {}", managerId);
        Team team = teamRepository.findByManagerId(managerId)
                .orElseThrow(() -> new NotFoundException("No team found for this manager id: " + managerId));
        return teamMapper.toDto(team);
    }


    @Override
    @Transactional
    public void addMember(Long teamId, Long userId) {
        logger.info("Adding member with userId {} to team {}", userId, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + teamId));

        UserDto user = webClient.get()
                .uri("/find?id=" + userId)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> {
                    logger.error("User not found with id: {}", userId);
                    return response.createException().flatMap(error ->
                            Mono.error(new NotFoundException("User not found with id: " + userId))
                    );
                })
                .bodyToMono(UserDto.class)
                .block(); // Blocking call, use async handling in production


        if (user == null) {
            logger.error("User not found with id: {}", userId);
            throw new NotFoundException("User not found with id: " + userId);
        }

        // Optional: Check if user is already a member (prevent duplicates)
        if (team.getMembers().stream().anyMatch(member -> member.getUserId().equals(userId))) {
            logger.warn("User with id {} is already a member of team {}", userId, teamId);
            throw new IllegalStateException("User already a member of the team");
        }

        TeamMember member = new TeamMember();
        member.setUserId(user.getId());
        member.setTeam(team);

        team.getMembers().add(member);
        teamRepository.save(team);
        teamMemberRepository.save(member);
        logger.info("Successfully added user {} to team {}", userId, teamId);
    }


    @Override
    @Transactional
    public TeamDto assignManager(Long teamId, Long managerId) {
        logger.info("Assigning manager with id {} to team {}", managerId, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + teamId));

        // Fetch manager details asynchronously
        Mono<UserDto> userMono = webClient.get()
                .uri("/find?id=" + managerId)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> {
                    logger.error("Manager not found with id: {}", managerId);
                    return response.createException().flatMap(error ->
                            Mono.error(new NotFoundException("Manager not found with id: " + managerId))
                    );
                })
                .bodyToMono(UserDto.class);

        UserDto user = userMono.block(); // Convert reactive response to synchronous

        if (user == null) {
            logger.error("Manager user not found with id: {}", managerId);
            throw new NotFoundException("User not found with id: " + managerId);
        }

        team.setManagerId(managerId);
        teamRepository.save(team);
        logger.info("Manager assigned successfully for team {}", teamId);
        return teamMapper.toDto(team);
    }


    @Override
    @Transactional
    public void removeMember(Long teamId, Long userId) {
        logger.info("Removing member with userId {} from team {}", userId, teamId);
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Team not found with id: " + teamId));

        TeamMember member = team.getMembers().stream()
                .filter(m -> m.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not found in team with id: " + userId));

        team.getMembers().remove(member);
        teamMemberRepository.delete(member);
        teamRepository.save(team);
        logger.info("Successfully removed member {} from team {}", userId, teamId);
    }
}
