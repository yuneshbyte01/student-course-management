package com.yuneshtimsina.studentcoursemanagement.controller;

import com.yuneshtimsina.studentcoursemanagement.model.Course;
import com.yuneshtimsina.studentcoursemanagement.model.Enrollment;
import com.yuneshtimsina.studentcoursemanagement.model.Student;
import com.yuneshtimsina.studentcoursemanagement.repository.CourseRepository;
import com.yuneshtimsina.studentcoursemanagement.repository.EnrollmentRepository;
import com.yuneshtimsina.studentcoursemanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> enrollStudent(@RequestParam int studentId, @RequestParam int courseId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (studentOpt.isEmpty()) {
            return new ResponseEntity<>("Student with ID " + studentId + " not found.", HttpStatus.NOT_FOUND);
        }

        if (courseOpt.isEmpty()) {
            return new ResponseEntity<>("Course with ID " + courseId + " not found.", HttpStatus.NOT_FOUND);
        }

        try {
            Enrollment enrollment = new Enrollment(studentOpt.get(), courseOpt.get());
            enrollmentRepository.save(enrollment);
            return new ResponseEntity<>("Enrolled successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server error during enrollment.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllEnrollments() {
        try {
            List<Enrollment> enrollments = enrollmentRepository.findAll();
            if (enrollments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(enrollments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
