package com.pkacz.schoolservice.controller;

import com.pkacz.schoolservice.model.dto.ParentBillingReportDto;
import com.pkacz.schoolservice.model.dto.SchoolBillingReportDto;
import com.pkacz.schoolservice.service.BillingService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/school")
@Validated
public class BillingController {

    private final BillingService billingService;

    @GetMapping("/{id}/billing")
    public SchoolBillingReportDto getSchoolBilling(@PathVariable("id") long schoolId,
                                                   @RequestParam("year") Integer year,
                                                   @RequestParam("month")
                                                   @Min(value = 1, message = "Min value for month is 0") @Max(value = 12, message = "Max value for month is 12") Integer month) {
        return billingService.getSchoolBillingDetails(schoolId, year, month);
    }

    @GetMapping("/{id}/parent/{parentId}/billing")
    public ParentBillingReportDto getSchoolBilling(@PathVariable("id") long schoolId, @PathVariable("parentId") long parentId,
                                                   @RequestParam("year") Integer year,
                                                   @RequestParam("month") @Min(value = 0, message = "Min value for month is 0") @Max(value = 12, message = "Max value for month is 12") Integer month) {
        return billingService.getParentBillingDetails(schoolId, parentId, year, month);
    }

}
