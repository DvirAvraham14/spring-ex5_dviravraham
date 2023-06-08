package hac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
//@EnableWebMvc // this annotation is needed to enable the @Controller annotation
//@ComponentScan(basePackages = {"hac.controllers", "hac.beans.repo"})
public class Ex5TemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ex5TemplateApplication.class, args);
    }

}
