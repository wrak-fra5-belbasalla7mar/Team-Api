package com.fawry.team_management_service.dto;

import com.fawry.team_management_service.dal.model.Team;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberDto {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("team_id")
    private Long teamId;

    private Team team;

}
