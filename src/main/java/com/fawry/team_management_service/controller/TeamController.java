package com.Fawry.Team_Management_service.controller;

import com.Fawry.Team_Management_service.dto.TeamDto;
import com.Fawry.Team_Management_service.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public List<TeamDto> getAllTeams() {
        return teamService.getAllTeams();
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) {
        TeamDto createdTeam = teamService.createTeam(teamDto);
        return ResponseEntity.created(URI.create("/teams/" + createdTeam.getId())).body(createdTeam);
    }

    @GetMapping("/{id}")
    public TeamDto getTeam(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }


//    @GetMapping // GET /teams?managerId=13&teamName=abdoTeam
//    public ResponseEntity<List<TeamDto>> getTeamsByManager(@RequestParam("managerId") Long managerId) {

    @GetMapping("/by-manager/{managerId}")
    public ResponseEntity<List<TeamDto>> getTeamsByManager(@PathVariable Long managerId) {
        List<TeamDto> teams = teamService.getTeamByManagerId(managerId);
        return ResponseEntity.ok(teams);
    }


    @PostMapping("/{teamId}/members/{userId}")
    public ResponseEntity<Void> addMember(@PathVariable Long teamId, @PathVariable Long userId) {
        teamService.addMember(teamId, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{teamId}/assign-manager/{managerId}")
    public ResponseEntity<TeamDto> assignManager(@PathVariable Long teamId, @PathVariable Long managerId) {
        return ResponseEntity.ok(teamService.assignManager(teamId, managerId));
    }

    @DeleteMapping("/{teamId}/remove-member/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long teamId, @PathVariable Long userId) {
        teamService.removeMember(teamId, userId);
        return ResponseEntity.noContent().build();
    }
}