package by.yahor_kulesh.config;

import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
    prefix = "connectionConfig",
    name = {"url", "username", "password"})
public class ConnectionConfig {
  @Value("${connectionConfig.url}")
  private String url;

  @Value("${connectionConfig.username}")
  private String user;

  @Value("${connectionConfig.password}")
  private String password;

  @Bean
  public DataSource dataSource() {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setURL(url);
    dataSource.setUser(user);
    dataSource.setPassword(password);
    return dataSource;
  }
}
