package com.yuneshtimsina.studentcoursemanagement.repository;

import com.yuneshtimsina.studentcoursemanagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
