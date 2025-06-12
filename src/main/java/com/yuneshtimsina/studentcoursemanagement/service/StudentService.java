package com.yuneshtimsina.studentcoursemanagement.service;

import com.yuneshtimsina.studentcoursemanagement.exception.EntityNotFoundException;
import com.yuneshtimsina.studentcoursemanagement.model.Course;
import com.yuneshtimsina.studentcoursemanagement.model.Enrollment;
import com.yuneshtimsina.studentcoursemanagement.model.Student;
import com.yuneshtimsina.studentcoursemanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + id + " not found."));
    }

    public Student createStudent(Student student) {
        validateStudent(student);
        checkEmailUniqueness(student.getEmail(), null);
        return studentRepository.save(student);
    }

    public Student updateStudent(int id, Student updatedStudent) {
        validateStudent(updatedStudent);
        Student existingStudent = getStudentById(id); // throws if not found
        checkEmailUniqueness(updatedStudent.getEmail(), id);

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setEmail(updatedStudent.getEmail());
        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(int id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student with ID " + id + " not found.");
        }
        studentRepository.deleteById(id);
    }

    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Student with email '" + email + "' not found."));
    }

    private void validateStudent(Student student) {
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Student name is required.");
        }
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Student email is required.");
        }
        if (!student.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    private void checkEmailUniqueness(String email, Integer currentStudentId) {
        studentRepository.findByEmail(email).ifPresent(existing -> {
            if (currentStudentId == null || existing.getId() != currentStudentId) {
                throw new IllegalArgumentException("Email '" + email + "' is already in use.");
            }
        });
    }

    public List<Course> getCoursesForStudent(int studentId) {
        Student student = getStudentById(studentId);
        return student.getEnrollments().stream()
                .map(Enrollment::getCourse)
                .toList();
    }

}
