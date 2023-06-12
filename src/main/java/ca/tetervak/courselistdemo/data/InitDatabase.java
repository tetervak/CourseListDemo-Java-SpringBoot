package ca.tetervak.courselistdemo.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitDatabase {

    private final Logger log = LoggerFactory.getLogger(InitDatabase.class);

    private final CourseRepository repository;

    public InitDatabase(CourseRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    void init() {
        Resource resource = new ClassPathResource("courses.json");
        try (
                FileInputStream file = new FileInputStream(resource.getFile());
                BufferedReader reader = new BufferedReader(new InputStreamReader(file))
        ) {
            Type listOfCourses = new TypeToken<ArrayList<Course>>() {}.getType();
            List<Course> courses = new Gson().fromJson(reader, listOfCourses);
            repository.saveAll(courses);
        } catch (IOException e) {
            log.error("Cannot load the data file.");
        }
    }
}
