package sk.kosickakademia.mizak.soloproject;

import sk.kosickakademia.mizak.soloproject.services.AppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@SpringBootApplication
public class App{
    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }
}
