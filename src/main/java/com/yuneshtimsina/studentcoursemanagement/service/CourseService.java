package com.yuneshtimsina.studentcoursemanagement.service;

import com.yuneshtimsina.studentcoursemanagement.model.Course;
import com.yuneshtimsina.studentcoursemanagement.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        // Optional: Enforce unique title
        // if (!courseRepository.findByTitleContainingIgnoreCase(course.getTitle()).isEmpty()) {
        //     throw new IllegalArgumentException("Course with a similar title already exists.");
        // }

        return courseRepository.save(course);
    }

    public Optional<Course> updateCourse(int id, Course updatedCourse) {
        validateCourse(updatedCourse);

        return courseRepository.findById(id).map(course -> {
            course.setTitle(updatedCourse.getTitle());
            course.setDescription(updatedCourse.getDescription());
            return courseRepository.save(course);
        });
    }

    public boolean deleteCourse(int id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Course> searchCoursesByTitle(String title) {
        return courseRepository.findByTitleContainingIgnoreCase(title);
    }

    public Optional<Course> getCourseById(int id) {
        return courseRepository.findById(id);
    }

    private void validateCourse(Course course) {
        if (course.getTitle() == null || course.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Course title must not be empty.");
        }
    }
}
