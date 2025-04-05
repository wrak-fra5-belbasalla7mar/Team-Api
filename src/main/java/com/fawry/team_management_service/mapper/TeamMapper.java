package com.fawry.team_management_service.mapper;

import com.fawry.team_management_service.dal.model.Team;
import com.fawry.team_management_service.dto.TeamDto;
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