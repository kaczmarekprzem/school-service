package com.pkacz.schoolservice.service;

import com.pkacz.schoolservice.factory.ReportFactory;
import com.pkacz.schoolservice.model.dto.ParentBillingReportDto;
import com.pkacz.schoolservice.model.dto.SchoolBillingReportDto;
import com.pkacz.schoolservice.model.entity.Parent;
import com.pkacz.schoolservice.model.entity.School;
import com.pkacz.schoolservice.repository.ParentRepository;
import com.pkacz.schoolservice.repository.SchoolRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {

    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private ParentRepository parentRepository;
    @Mock
    private ReportFactory reportFactory;
    @InjectMocks
    private BillingService billingService;

    @Test
    void testGetSchoolBillingDetails() {
        // Given
        School school = new School(1L, "School", BigDecimal.TEN, Collections.emptySet());
        Parent parent = new Parent(1L, "John", "Doe", Collections.emptySet());
        SchoolBillingReportDto schoolBillingReportDto = SchoolBillingReportDto.builder()
                .totalFees(BigDecimal.TEN)
                .build();
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(parentRepository.getParentsForReport(1, 2024, 2)).thenReturn(List.of(parent));
        when(reportFactory.createSchoolBillingReport(any(), any())).thenReturn(schoolBillingReportDto);

        // When
        SchoolBillingReportDto result = billingService.getSchoolBillingDetails(1, 2024, 2);

        // Then
        assertEquals(BigDecimal.TEN, result.totalFees());
    }

    @Test
    void testGetParentBillingDetails() {
        // Given
        School school = new School(1L, "School", BigDecimal.TEN, Collections.emptySet());
        Parent parent = new Parent(1L, "John", "Doe", Collections.emptySet());
        ParentBillingReportDto parentBillingReportDto = ParentBillingReportDto.builder().totalFees(BigDecimal.TEN).build();

        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(parentRepository.getParentForReport(1L, 2024, 2, 1)).thenReturn(Optional.of(parent));
        when(reportFactory.createParentBillingReport(any(), any())).thenReturn(parentBillingReportDto);

        // When
        ParentBillingReportDto result = billingService.getParentBillingDetails(1, 1, 2024, 2);

        // Then
        assertEquals(BigDecimal.TEN, result.totalFees());
    }
}
