package com.yuneshtimsina.studentcoursemanagement.controller;

import com.yuneshtimsina.studentcoursemanagement.model.Course;
import com.yuneshtimsina.studentcoursemanagement.model.Enrollment;
import com.yuneshtimsina.studentcoursemanagement.model.Student;
import com.yuneshtimsina.studentcoursemanagement.repository.CourseRepository;
import com.yuneshtimsina.studentcoursemanagement.repository.EnrollmentRepository;
import com.yuneshtimsina.studentcoursemanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public EnrollmentController(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping
    public ResponseEntity<String> enrollStudent(@RequestParam int studentId, @RequestParam int courseId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (studentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Student with ID " + studentId + " does not exist.");
        }

        if (courseOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Course with ID " + courseId + " does not exist.");
        }

        Student student = studentOpt.get();
        Course course = courseOpt.get();

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);

        return ResponseEntity.ok("Student " + studentId + " enrolled in course " + courseId + " successfully.");
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }


}
