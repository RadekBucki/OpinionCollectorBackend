package pl.opinion_collector.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class OpinionCollectorBackendApplication {
    /**
     * Application start method
     *
     * @param args init app arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(OpinionCollectorBackendApplication.class, args);
    }
}
