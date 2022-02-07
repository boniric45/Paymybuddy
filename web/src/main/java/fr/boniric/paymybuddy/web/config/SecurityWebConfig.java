package fr.boniric.paymybuddy.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

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

        String[] staticResources = {
                "/css/**",
                "/images/**",
                "/fonts/**",
                "/scripts/**",
        };

        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/inscription").permitAll()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers(HttpMethod.GET, "/contact").authenticated()
                .antMatchers(HttpMethod.GET, "/contact/**").authenticated()
                .antMatchers(HttpMethod.POST, "/contact").authenticated()
                .mvcMatchers(HttpMethod.GET, "/contact/all").permitAll()
                .antMatchers(HttpMethod.GET, "/transfer").authenticated()
                .antMatchers(HttpMethod.POST, "/transfer").authenticated()
                .antMatchers(HttpMethod.GET, "/recapRegister").permitAll()
                .antMatchers(HttpMethod.GET, "/username").authenticated()
                .antMatchers(HttpMethod.GET, "/addconnection").authenticated()
                .antMatchers(HttpMethod.POST, "/addconnection").authenticated()
                .antMatchers(HttpMethod.GET, "/pay").authenticated()
                .antMatchers(HttpMethod.POST, "/pay").authenticated()
                .antMatchers(HttpMethod.GET, "/transaction/**").authenticated()
                .antMatchers(HttpMethod.POST, "/transaction/**").authenticated()
                .antMatchers(HttpMethod.GET, "/recapTransaction").authenticated()
                .antMatchers(HttpMethod.POST, "/recapTransaction").authenticated()
                .and()
                .formLogin()//connexion par formulaire autorisé
                .usernameParameter("username")//champ formulaire
                .passwordParameter("password")
                .loginPage("/login").permitAll() // forms custom login
                       .failureUrl("/bad")// if ko bad
                .defaultSuccessUrl("/transfer")// if ok transfer
                .and()
                .logout();
    }

    //Authorize ressources
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/images/**"); // #3
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }


}
