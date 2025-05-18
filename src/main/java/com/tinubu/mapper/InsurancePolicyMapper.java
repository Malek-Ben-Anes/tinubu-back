package com.tinubu.mapper;

import com.tinubu.dto.InsurancePolicyDto;
import com.tinubu.dto.InsurancePolicySaveDto;
import com.tinubu.entity.InsurancePolicyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InsurancePolicyMapper {

    InsurancePolicyMapper INSTANCE = Mappers.getMapper(InsurancePolicyMapper.class);

    InsurancePolicyEntity toEntity(InsurancePolicySaveDto saveDto);

    InsurancePolicyDto toDto(InsurancePolicyEntity policy);

    List<InsurancePolicyDto> toDtos(List<InsurancePolicyEntity> policy);

}