package com.pkacz.schoolservice.factory;

import com.pkacz.schoolservice.model.dto.ChildAttendanceDto;
import com.pkacz.schoolservice.model.dto.ChildReportDto;
import com.pkacz.schoolservice.model.dto.ParentBillingReportDto;
import com.pkacz.schoolservice.model.dto.SchoolBillingReportDto;
import com.pkacz.schoolservice.model.entity.Attendance;
import com.pkacz.schoolservice.model.entity.Child;
import com.pkacz.schoolservice.model.entity.Parent;
import com.pkacz.schoolservice.model.entity.School;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Component
public class ReportFactory {

    @Value("${school.start.free.hours}")
    private LocalTime startFreeHours;
    @Value("${school.end.free.hours}")
    private LocalTime endFreeHours;

    public ParentBillingReportDto createParentBillingReport(Parent parent, School school) {
        List<ChildReportDto> childrenReport = createChildrenReport(parent.getChildren());
        int numberOfPaidHours = sumPaidHours(childrenReport);
        return ParentBillingReportDto.builder()
                .parentId(parent.getId())
                .childrenReport(childrenReport)
                .numberOfPaidHours(numberOfPaidHours)
                .totalFees(BigDecimal.valueOf(numberOfPaidHours).multiply(school.getHourPrice()))
                .build();
    }

    public SchoolBillingReportDto createSchoolBillingReport(School school, List<Parent> parents) {
        List<ParentBillingReportDto> parentsBillingReport = createParentsBillingReport(parents, school);

        return SchoolBillingReportDto.builder()
                .parentBillingReports(createParentsBillingReport(parents, school))
                .totalFees(parentsBillingReport.stream()
                        .map(ParentBillingReportDto::totalFees)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }

    private List<ParentBillingReportDto> createParentsBillingReport(List<Parent> parents, School school) {
        return parents.stream()
                .map(parent -> createParentBillingReport(parent, school))
                .toList();
    }

    private List<ChildReportDto> createChildrenReport(Set<Child> children) {
        return children.stream()
                .map(this::createChildReport)
                .toList();
    }

    private ChildReportDto createChildReport(Child child) {
        return ChildReportDto.builder()
                .childId(child.getId())
                .attendances(toChildAttendances(child.getAttendances()))
                .firstName(child.getFirstName())
                .lastName(child.getLastName())
                .numberOfPaidHours(getNumberOfPaidHours(child.getAttendances()))
                .build();
    }

    private Integer getNumberOfPaidHours(Set<Attendance> attendances) {
        int numberOfPaidHours = 0;
        for (var attendance : attendances) {
            if (attendance.getEntryDate().isBefore(startFreeHours.atDate(attendance.getEntryDate().toLocalDate()))) {
                numberOfPaidHours += (int) Math.ceil(Duration.between(attendance.getEntryDate(), startFreeHours.atDate(attendance.getEntryDate().toLocalDate())).toMinutes() / 60.00);
            }
            if (attendance.getExitDate().isAfter(endFreeHours.atDate(attendance.getExitDate().toLocalDate()))) {
                numberOfPaidHours += (int) Math.ceil(Duration.between(endFreeHours.atDate(attendance.getExitDate().toLocalDate()), attendance.getExitDate()).toMinutes() / 60.00);
            }
        }
        return numberOfPaidHours;
    }

    private Integer sumPaidHours(List<ChildReportDto> childReports) {
        return childReports.stream()
                .mapToInt(ChildReportDto::numberOfPaidHours)
                .sum();
    }

    private List<ChildAttendanceDto> toChildAttendances(Set<Attendance> attendances) {
        return attendances.stream()
                .map(attendance -> ChildAttendanceDto.builder()
                        .entryDate(attendance.getEntryDate())
                        .exitDate(attendance.getExitDate())
                        .build())
                .toList();
    }

}
