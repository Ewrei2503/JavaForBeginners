package by.yahor_kulesh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.io.File;
import java.io.IOException;


@Configuration
@ComponentScan("by.yahor_kulesh")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableJpaRepositories("by.yahor_kulesh")
public class ApplicationContext implements WebMvcConfigurer {

    @Bean
    public File ticketDataFile() {
        try {
            return new ClassPathResource("ticketData.txt").getFile();
        } catch (IOException e) {
            return null;
        }
    }
}