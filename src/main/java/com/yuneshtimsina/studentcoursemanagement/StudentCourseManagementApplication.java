package com.yuneshtimsina.studentcoursemanagement;

import com.yuneshtimsina.studentcoursemanagement.model.Student;
import com.yuneshtimsina.studentcoursemanagement.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentCourseManagementApplication {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentCourseManagementApplication(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(StudentCourseManagementApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // Create fake student
        Student dummy = new Student("Test User", "testuser@example.com");

        // Save to DB
        studentRepository.save(dummy);

        // Fetch and print all students
        System.out.println("📋 All Students in Database:");
        studentRepository.findAll().forEach(System.out::println);
    }
}
