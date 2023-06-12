package ca.tetervak.courselistdemo.controller;

import ca.tetervak.courselistdemo.data.Course;
import ca.tetervak.courselistdemo.data.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CourseDataController {

    private final Logger log = LoggerFactory.getLogger(CourseDataController.class);

    private final CourseRepository repository;

    public CourseDataController(CourseRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(){
        return "Index";
    }

    @GetMapping("/list-all")
    public ModelAndView listAllCourses(){
        List<Course> courses = repository.findAll();
        return new ModelAndView("ListAllCourses", "courses", courses);
    }

    @GetMapping("/list-by-pages")
    public ModelAndView listCoursesByPages(@RequestParam(defaultValue = "0") int p){

        Page<Course> page = repository.findAll(PageRequest.of(p,10));
        return new ModelAndView("ListCoursesByPages", "page", page);
    }

    @GetMapping("/list-by-codes")
    public String listCoursesByCodes(
            @RequestParam(defaultValue = "PROG") String starts,
            Model model
    ){
        String[] codeLetters = {"APPL", "COMM", "DBAS", "DSGN", "INFO", "MATH", "MEDA", "PROG", "SYST", "TELE"};
        List<Course> courses = repository.findByCodeStartsWith(starts);
        model.addAttribute("codeLetters", codeLetters);
        model.addAttribute("selectedLetters", starts);
        model.addAttribute("courses", courses);
        return"ListCoursesByCodes";
    }

}
