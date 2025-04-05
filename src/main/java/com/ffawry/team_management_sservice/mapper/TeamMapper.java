package com.ffawry.team_management_sservice.mapper;

import com.ffawry.team_management_sservice.dal.model.Team;
import com.ffawry.team_management_sservice.dto.TeamDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

    TeamDto toDto(Team team);
    Team toEntity(TeamDto teamDto);
}