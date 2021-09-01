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
                .antMatchers(HttpMethod.GET, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/inscription").permitAll()
                .antMatchers(HttpMethod.GET,"/contact").authenticated()
                .antMatchers(HttpMethod.POST,"/contact").authenticated()
                .antMatchers(HttpMethod.GET, "/transfer").authenticated()
                .antMatchers(HttpMethod.GET, "recapRegister").permitAll()
                .antMatchers(HttpMethod.GET, "/username").authenticated() //Récupère l'identifiant
                .antMatchers(HttpMethod.GET, "/addconnection").authenticated()
                .antMatchers(HttpMethod.POST, "/addconnection").authenticated()
                .antMatchers(HttpMethod.GET, "/pay").authenticated()
                .antMatchers(HttpMethod.POST, "/pay").authenticated()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .and()
                .formLogin()//connexion par formulaire autorisé
                .usernameParameter("username")//champ formulaire
                .passwordParameter("password")
                .loginPage("/login").permitAll() // formulaire de login personnalisé
                .failureUrl("/bad")// si ko renvoi bad
                .defaultSuccessUrl("/transfer")// si ok renvoi payment
                .and()
                .csrf().disable();//Sécurité

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
