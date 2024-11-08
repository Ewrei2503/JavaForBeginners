package by.yahor_kulesh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;


@Configuration
public class ApplicationContext{

    @Bean
    public File ticketDataFile() {
        try {
            return new ClassPathResource("ticketData.txt").getFile();
        } catch (IOException e) {
            return null;
        }
    }
}