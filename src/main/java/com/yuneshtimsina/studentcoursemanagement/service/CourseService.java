package com.yuneshtimsina.studentcoursemanagement.service;

import com.yuneshtimsina.studentcoursemanagement.exception.EntityNotFoundException;
import com.yuneshtimsina.studentcoursemanagement.model.Course;
import com.yuneshtimsina.studentcoursemanagement.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course) {
        validateCourse(course);

        if (courseRepository.existsByTitleIgnoreCase(course.getTitle())) {
            throw new IllegalArgumentException("Course with title '" + course.getTitle() + "' already exists.");
        }

        return courseRepository.save(course);
    }

    public Course updateCourse(int id, Course updatedCourse) {
        validateCourse(updatedCourse);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID " + id));

        course.setTitle(updatedCourse.getTitle());
        course.setDescription(updatedCourse.getDescription());
        return courseRepository.save(course);
    }

    public void deleteCourse(int id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Course not found with ID " + id);
        }
        courseRepository.deleteById(id);
    }

    public List<Course> searchCoursesByTitle(String title) {
        return courseRepository.findByTitleContainingIgnoreCase(title);
    }

    public Course getCourseById(int id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID " + id));
    }

    private void validateCourse(Course course) {
        if (course.getTitle() == null || course.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Course title must not be empty.");
        }
    }
}