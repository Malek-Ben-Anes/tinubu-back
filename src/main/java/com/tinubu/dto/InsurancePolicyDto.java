package com.tinubu.dto;

import com.tinubu.enums.InsurancePolicyStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InsurancePolicyDto {

    private Long id;

    private String name;

    private InsurancePolicyStatus status;

    private LocalDateTime coverageStartDate;

    private LocalDateTime coverageEndDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
