package com.pkacz.schoolservice.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ParentBillingReportDto(Long parentId, BigDecimal totalFees, int numberOfPaidHours,
                                     List<ChildReportDto> childrenReport) {
}
