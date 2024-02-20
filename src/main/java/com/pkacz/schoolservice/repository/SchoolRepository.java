package com.pkacz.schoolservice.repository;

import com.pkacz.schoolservice.model.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {

}
