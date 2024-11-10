package by.yahor_kulesh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults())
        .csrf(
            AbstractHttpConfigurer
                ::disable); // to enable post methods working needed to disable CSRF-token
    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails userDetails =
        User.withDefaultPasswordEncoder().username("user").password("password").build();
    return new InMemoryUserDetailsManager(userDetails);
  }
}
