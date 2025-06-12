package com.yuneshtimsina.studentcoursemanagement.controller;

import com.yuneshtimsina.studentcoursemanagement.model.Student;
import com.yuneshtimsina.studentcoursemanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            List<Student> students = studentRepository.findAll();
            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        try {
            Student saved = studentRepository.save(student);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create student.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setName(updatedStudent.getName());
            student.setEmail(updatedStudent.getEmail());
            return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Student not found.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        try {
            if (studentRepository.existsById(id)) {
                studentRepository.deleteById(id);
                return new ResponseEntity<>("Student deleted successfully.", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Student not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting student.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchByEmail")
    public ResponseEntity<?> getStudentByEmail(@RequestParam String email) {
        try {
            Optional<Student> studentOpt = studentRepository.findByEmail(email);
            if (studentOpt.isPresent()) {
                return new ResponseEntity<>(studentOpt.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No student found with email: " + email, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
