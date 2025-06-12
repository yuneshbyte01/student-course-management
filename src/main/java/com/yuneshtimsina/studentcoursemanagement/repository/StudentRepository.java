package com.yuneshtimsina.studentcoursemanagement.repository;

import com.yuneshtimsina.studentcoursemanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
