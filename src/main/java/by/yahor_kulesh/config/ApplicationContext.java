package by.yahor_kulesh.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("by.yahor_kulesh")
@PropertySource("classpath:application.properties")
public class ApplicationContext {
}