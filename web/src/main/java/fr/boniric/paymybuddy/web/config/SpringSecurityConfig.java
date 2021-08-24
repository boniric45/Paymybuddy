package fr.boniric.paymybuddy.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

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
                .antMatchers("/login").hasRole("USER").anyRequest().authenticated()
                .and()
                .formLogin()//connexion par formulaire autorisé
                .usernameParameter("username")//champ formulaire
                .passwordParameter("password")
                .loginPage("/login").permitAll() // formulaire de login personnalisé
                .failureUrl("/bad").permitAll()// si ko renvoi bad
                .defaultSuccessUrl("/payment")// si ok renvoi payment
                .and()
                .csrf().disable();//champ formulaire

    }

    //Authorise les resources
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

}
