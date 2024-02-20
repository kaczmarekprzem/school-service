package com.pkacz.schoolservice.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChildAttendanceDto(LocalDateTime entryDate, LocalDateTime exitDate) {
}
