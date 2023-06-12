package ca.tetervak.courselistdemo.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findByCodeStartsWith(String starts);
}
