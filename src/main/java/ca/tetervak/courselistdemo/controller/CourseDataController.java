package ca.tetervak.courselistdemo.controller;

import ca.tetervak.courselistdemo.data.Course;
import ca.tetervak.courselistdemo.data.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


    @GetMapping("/list-by-codes")
    public String listCoursesByCodesDefault(){
        return "redirect:/list-by-codes/PROG";
    }

    @GetMapping("/list-by-codes/{letters}")
    public String listCoursesByCodes(
            @PathVariable String letters,
            Model model
    ){
        String[] codeLetters =
                {"APPL", "COMM", "DBAS", "DSGN", "INFO", "MATH", "MEDA", "PROG", "SYST", "TELE"};
        List<Course> courses = repository.findByCodeStartsWith(letters);
        model.addAttribute("codeLetters", codeLetters);
        model.addAttribute("selectedLetters", letters);
        model.addAttribute("courses", courses);
        return"ListCoursesByCodes";
    }

    @GetMapping("/list-by-pages")
    public ModelAndView listCoursesByPages(@RequestParam(defaultValue = "0") int p){
        Page<Course> page = repository.findAll(PageRequest.of(p,10));
        return new ModelAndView("ListCoursesByPages", "page", page);
    }

    @GetMapping("/list-by-codes-and-pages")
    public String listCoursesByCodesAndPagesDefault(){
        return "redirect:/list-by-codes-and-pages/PROG";
    }

    @GetMapping("/list-by-codes-and-pages/{letters}")
    public String listCoursesByCodesAndPages(
            @PathVariable String letters,
            @RequestParam(defaultValue = "0") int p,
            Model model
    ){
        String[] codeLetters =
                {"APPL", "COMM", "DBAS", "DSGN", "INFO", "MATH", "MEDA", "PROG", "SYST", "TELE"};
        model.addAttribute("codeLetters", codeLetters);
        model.addAttribute("selectedLetters", letters);

        Page<Course> page = repository.findByCodeStartsWith(letters,PageRequest.of(p,10));
        model.addAttribute("page", page);
        return "ListCoursesByCodesAndPages";
    }

    @GetMapping("/list-all-sorted")
    public String listAllCoursesSorted(
            @RequestParam(defaultValue = "ASC") String d,
            Model model
    ){
        Sort.Direction direction = d.equals("DESC")?Sort.Direction.DESC:Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "code", "name");
        List<Course> courses = repository.findAll(sort);
        model.addAttribute("courses", courses);
        log.debug("direction=" + direction);
        model.addAttribute("direction", direction.toString());
        return "ListAllCoursesSorted";
    }

    @GetMapping("/list-sorted-by-pages")
    public String listSortedCoursesByPages(
            @RequestParam(defaultValue = "0") int p,
            @RequestParam(defaultValue = "ASC") String d,
            Model model
    ){
        Sort.Direction direction = d.equals("DESC")?Sort.Direction.DESC:Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "code", "name");
        Page<Course> page = repository.findAll(PageRequest.of(p,10, sort));
        model.addAttribute("page", page);
        log.debug("direction=" + direction);
        model.addAttribute("direction", direction.toString());
        return "ListSortedCoursesByPages";
    }

}
