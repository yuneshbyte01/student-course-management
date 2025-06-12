package com.yuneshtimsina.studentcoursemanagement.repository;

import com.yuneshtimsina.studentcoursemanagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByTitleContainingIgnoreCase(String title);

    boolean existsByTitleIgnoreCase(String title);
}
