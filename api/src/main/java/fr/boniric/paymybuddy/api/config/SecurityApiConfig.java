package fr.boniric.paymybuddy.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityApiConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        //  Authentification bdd
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select users_email, users_password, true from users where users_email=?")
                .authoritiesByUsernameQuery("select users_email, users_roles from users where users_email=?");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/user/**").permitAll()// GET requests don't need auth
                .mvcMatchers(HttpMethod.POST, "/user/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/users/**").permitAll()
                .mvcMatchers(HttpMethod.PUT, "/updateBalance/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/inscription").permitAll()
                .mvcMatchers(HttpMethod.GET, "/contact/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/listcontact/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/contact/all").permitAll()
                .mvcMatchers(HttpMethod.POST, "/contact").permitAll()
                .mvcMatchers(HttpMethod.POST, "/addconnection/**").permitAll()
                .mvcMatchers(HttpMethod.DELETE, "/deleteContact/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/payment_type").permitAll()
                .mvcMatchers(HttpMethod.GET, "/transaction/**").permitAll()
                .mvcMatchers(HttpMethod.POST, "/transaction/**").permitAll()
                .mvcMatchers(HttpMethod.PUT, "/transaction/**").permitAll()
                .mvcMatchers(HttpMethod.DELETE, "/deleteUser/**").permitAll()
                .anyRequest()
                .authenticated();

        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

}
