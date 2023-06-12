package ca.tetervak.courselistdemo.controller;

import ca.tetervak.courselistdemo.data.Course;
import ca.tetervak.courselistdemo.data.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CourseDataController {

    private final Logger log = LoggerFactory.getLogger(CourseDataController.class);

    private final CourseRepository repository;

    public CourseDataController(CourseRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = {"/", "/courses"})
    ModelAndView listCourses(@RequestParam(defaultValue = "0") int p){

        Page<Course> page = repository.findAll(PageRequest.of(p,10));
        return new ModelAndView("Courses", "page", page);
    }

}
