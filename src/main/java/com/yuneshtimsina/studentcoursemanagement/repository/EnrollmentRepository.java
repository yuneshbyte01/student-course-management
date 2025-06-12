package com.yuneshtimsina.studentcoursemanagement.repository;

import com.yuneshtimsina.studentcoursemanagement.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
}
