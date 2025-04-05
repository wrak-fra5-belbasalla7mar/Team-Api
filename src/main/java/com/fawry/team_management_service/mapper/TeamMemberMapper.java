package com.fawry.team_management_service.mapper;

import com.fawry.team_management_service.dto.TeamMemberDto;
import com.fawry.team_management_service.dal.model.TeamMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMemberMapper {
    TeamMemberDto toDto(TeamMember teamMember);
    TeamMember toEntity(TeamMemberDto teamMemberDto);
}