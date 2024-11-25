package com.MTGCollectionTracker.MTGCollectionTrackerApplication.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;

/**
 * security configurations for application
 *
 * @author timmonsevan
 */
@Configuration
public class SecurityConfig {

    /**
     * instructs application on JDBC to use to gather security credentials from local database
     */
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT user_id, pw, active FROM users WHERE user_id=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT user_id, role FROM roles WHERE user_id=?");

        return jdbcUserDetailsManager;
    }

    /**
     * assigns HTTP requests to specific roles (lowest permissions to highest) USER, TESTER, and ADMIN
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(configurer ->
                        configurer
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/loginPage")
                                .loginProcessingUrl("/authenticateUser")
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                );

        /**
         * HTTP Basic authentication
         */

        httpSecurity.httpBasic(Customizer.withDefaults());

        /**
         * disable CSRF
         */

        httpSecurity.csrf(csrf -> csrf.disable());

        return httpSecurity.build();
    }
}
