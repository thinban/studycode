package com.example.thinbanrest;

import com.example.thinbanrest.domain.Person;
import com.example.thinbanrest.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories
@ServletComponentScan
public class ThinbanRestApplication implements WebMvcConfigurer {

    @Bean
//    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ThinbanRestApplication.class, args);
        PersonRepository personRepository = context.getBean(PersonRepository.class);
        personRepository.save(new Person("Hans", "Meiser"));
        personRepository.save(new Person("Peter", "Lustig"));

        try {
            Runtime.getRuntime().exec("cmd /c start http://localhost:8080");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
