package by.yahor_kulesh.config;

import java.io.File;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ApplicationContext {

  @Bean
  public File ticketDataFile() {
    try {
      return new ClassPathResource("ticketData.txt").getFile();
    } catch (IOException e) {
      return null;
    }
  }
}
