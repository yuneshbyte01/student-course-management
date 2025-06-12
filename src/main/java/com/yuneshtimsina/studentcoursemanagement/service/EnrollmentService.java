package com.yuneshtimsina.studentcoursemanagement.service;

import com.yuneshtimsina.studentcoursemanagement.model.Course;
import com.yuneshtimsina.studentcoursemanagement.model.Enrollment;
import com.yuneshtimsina.studentcoursemanagement.model.Student;
import com.yuneshtimsina.studentcoursemanagement.repository.CourseRepository;
import com.yuneshtimsina.studentcoursemanagement.repository.EnrollmentRepository;
import com.yuneshtimsina.studentcoursemanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<String> enrollStudent(int studentId, int courseId) {

        if (studentId <= 0 || courseId <= 0) {
            return Optional.of("Invalid student or course ID.");
        }

        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            return Optional.of("Student with ID " + studentId + " not found.");
        }

        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            return Optional.of("Course with ID " + courseId + " not found.");
        }

        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            return Optional.of("Student is already enrolled in this course.");
        }

        Enrollment enrollment = new Enrollment(studentOpt.get(), courseOpt.get());
        enrollmentRepository.save(enrollment);

        return Optional.empty();
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public boolean isEnrolled(int studentId, int courseId) {
        if (studentId <= 0 || courseId <= 0) {
            throw new IllegalArgumentException("Invalid student or course ID.");
        }
        return enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }
}
