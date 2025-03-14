package com.Fawry.Team_Management_service.mapper;

import com.Fawry.Team_Management_service.dal.model.Team;
import com.Fawry.Team_Management_service.dto.TeamDto;
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