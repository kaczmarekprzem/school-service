package com.pkacz.schoolservice.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record SchoolBillingReportDto(BigDecimal totalFees, List<ParentBillingReportDto> parentBillingReports) {
}
