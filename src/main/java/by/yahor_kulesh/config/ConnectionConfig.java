package by.yahor_kulesh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Component
public class ConnectionConfig {
    @Value("${connectionConfig.url}")
    private String url;
    @Value("${connectionConfig.username}")
    private String user;
    @Value("${connectionConfig.password}")
    private String password;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}