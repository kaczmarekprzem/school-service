package com.pkacz.schoolservice.service;

import com.pkacz.schoolservice.factory.ReportFactory;
import com.pkacz.schoolservice.model.dto.ParentBillingReportDto;
import com.pkacz.schoolservice.model.dto.SchoolBillingReportDto;
import com.pkacz.schoolservice.repository.ParentRepository;
import com.pkacz.schoolservice.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final SchoolRepository schoolRepository;
    private final ParentRepository parentRepository;
    private final ReportFactory reportFactory;

    public SchoolBillingReportDto getSchoolBillingDetails(long schoolId, int year, int month) {
        var school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new IllegalArgumentException("School with given id does not exist"));
        var parents = parentRepository.getParentsForReport(schoolId, year, month);

        return reportFactory.createSchoolBillingReport(school, parents);
    }

    public ParentBillingReportDto getParentBillingDetails(long schoolId, long parentId, int year, int month) {
        var school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new IllegalArgumentException("School with given id does not exist"));

        var parent = parentRepository.getParentForReport(schoolId, year, month, parentId)
                .orElseThrow(() -> new IllegalArgumentException("School with given id does not exist"));
        return reportFactory.createParentBillingReport(parent, school);
    }

}
