package com.Fawry.Team_Management_service.mapper;

import com.Fawry.Team_Management_service.dto.TeamMemberDto;
import com.Fawry.Team_Management_service.dal.model.TeamMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMemberMapper {
    TeamMemberDto toDto(TeamMember teamMember);
    TeamMember toEntity(TeamMemberDto teamMemberDto);
}