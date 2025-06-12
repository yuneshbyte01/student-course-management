package com.yuneshtimsina.studentcoursemanagement.service;

import com.yuneshtimsina.studentcoursemanagement.exception.EntityNotFoundException;
import com.yuneshtimsina.studentcoursemanagement.model.Course;
import com.yuneshtimsina.studentcoursemanagement.model.Enrollment;
import com.yuneshtimsina.studentcoursemanagement.model.Student;
import com.yuneshtimsina.studentcoursemanagement.repository.CourseRepository;
import com.yuneshtimsina.studentcoursemanagement.repository.EnrollmentRepository;
import com.yuneshtimsina.studentcoursemanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public void enrollStudent(int studentId, int courseId) {
        if (studentId <= 0 || courseId <= 0) {
            throw new IllegalArgumentException("Student ID and Course ID must be positive integers.");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " not found."));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " not found."));

        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new IllegalArgumentException("Student is already enrolled in this course.");
        }

        Enrollment enrollment = new Enrollment(student, course);
        enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public boolean isEnrolled(int studentId, int courseId) {
        if (studentId <= 0 || courseId <= 0) {
            throw new IllegalArgumentException("Student ID and Course ID must be positive integers.");
        }
        return enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }
}
