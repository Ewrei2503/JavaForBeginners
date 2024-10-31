package by.yahor_kulesh.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class ConnectionConfig {
    @Value("${connectionConfig.url}")
    private String url;
    @Value("${connectionConfig.username}")
    private String user;
    @Value("${connectionConfig.password}")
    private String password;

    public DataSource dataSource(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}