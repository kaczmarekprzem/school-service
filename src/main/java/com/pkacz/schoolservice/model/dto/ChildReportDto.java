package com.pkacz.schoolservice.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ChildReportDto(Long childId, String firstName, String lastName, List<ChildAttendanceDto> attendances,
                             Integer numberOfPaidHours) {
}
