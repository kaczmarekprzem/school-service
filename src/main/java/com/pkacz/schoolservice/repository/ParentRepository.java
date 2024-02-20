package com.pkacz.schoolservice.repository;

import com.pkacz.schoolservice.model.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    @Query("SELECT DISTINCT p FROM Parent p " +
            "LEFT JOIN FETCH p.children c " +
            "LEFT JOIN FETCH c.school " +
            "LEFT JOIN FETCH c.attendances a " +
            "WHERE c.school.id = :schoolId " +
            "AND p.id = :parentId " +
            "AND YEAR(a.entryDate) = :year " +
            "AND MONTH(a.entryDate) = :month")
    Optional<Parent> getParentForReport(long schoolId, int year, int month, long parentId);

    @Query("SELECT DISTINCT p FROM Parent p " +
            "LEFT JOIN FETCH p.children c " +
            "LEFT JOIN FETCH c.school " +
            "LEFT JOIN FETCH c.attendances a " +
            "WHERE c.school.id = :schoolId " +
            "AND YEAR(a.entryDate) = :year " +
            "AND MONTH(a.entryDate) = :month")
    List<Parent> getParentsForReport(long schoolId, int year, int month);
}
