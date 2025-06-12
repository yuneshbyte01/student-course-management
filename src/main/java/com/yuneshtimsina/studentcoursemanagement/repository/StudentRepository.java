package com.yuneshtimsina.studentcoursemanagement.repository;

import com.yuneshtimsina.studentcoursemanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByEmail(String email);

}
