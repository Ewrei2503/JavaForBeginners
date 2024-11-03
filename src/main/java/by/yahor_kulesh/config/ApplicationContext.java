package by.yahor_kulesh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;


@Configuration
@ComponentScan("by.yahor_kulesh")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class ApplicationContext {

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public File ticketData() {
        try {
            return new ClassPathResource("ticketData.txt").getFile();
        } catch (IOException e) {
            return null;
        }
    }
}