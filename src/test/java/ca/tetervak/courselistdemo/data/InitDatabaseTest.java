package ca.tetervak.courselistdemo.data;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;

import static java.lang.System.out;

@SpringBootTest
class InitDatabaseTest {

    @Test
    void init() {
        Resource resource = new ClassPathResource("courses.json");
        try (FileInputStream file = new FileInputStream(resource.getFile())){
            String json = new String(file.readAllBytes());
            out.println(json);
            Course[] courses = new GsonBuilder().create().fromJson(json, Course[].class);
        } catch(IOException e){
            //
        }
    }
}
