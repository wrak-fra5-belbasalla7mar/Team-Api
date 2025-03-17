package com.Fawry.Team_Management_service.controller;

import com.Fawry.Team_Management_service.dto.TeamDto;
import com.Fawry.Team_Management_service.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@Data
@RestController
@RequestMapping("/teams")
@AllArgsConstructor
public class TeamController {

    private TeamService teamService;

    @GetMapping
    public List<TeamDto> getAllTeams() {
        return teamService.getAllTeams();
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) {
        TeamDto createdTeam = teamService.createTeam(teamDto);
        return ResponseEntity.created(URI.create("/teams/" + createdTeam.getId()))
                .body(createdTeam);
    }

    @GetMapping("/{id}")
    public TeamDto getTeam(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    @PostMapping("/{teamId}/members/{userId}")
    public ResponseEntity<Void> addMember(@PathVariable Long teamId, @PathVariable Long userId) {
        teamService.addMember(teamId, userId);
        return ResponseEntity.ok().build();
    }
}