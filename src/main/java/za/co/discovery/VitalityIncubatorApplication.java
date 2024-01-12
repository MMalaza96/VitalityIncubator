package za.co.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = "za.co.discovery")
public class VitalityIncubatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(VitalityIncubatorApplication.class, args);
    }

}
