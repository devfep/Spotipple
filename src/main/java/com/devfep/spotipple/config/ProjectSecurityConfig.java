package com.devfep.spotipple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // Permit All Requests inside the Web Application
//        http.authorizeRequests()
//                .anyRequest().permitAll().
//                and().formLogin()
//                .and().httpBasic();

        // Deny All Requests inside the Web Application
//            http.authorizeRequests().anyRequest().denyAll().
//                    and().formLogin()
//                    .and().httpBasic();

        // http.csrf().disable().authorizeRequests()//<-not necessary when using thymeleaf


        http.csrf().ignoringAntMatchers("/public/**").and()
                .authorizeRequests()
                .mvcMatchers("/home").permitAll()
                .mvcMatchers("/feature-request").permitAll()
                .mvcMatchers("/thank-you").authenticated()
                .mvcMatchers("/dashboard").authenticated()
                .mvcMatchers("/guide").permitAll()
                .mvcMatchers("/login").permitAll()
                .mvcMatchers("/public/**").permitAll()
                .antMatchers("/js/**", "/css/**").permitAll()
                .and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/spotify-login").failureUrl("/login?error=true").permitAll()
                .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
                .and().httpBasic();

        //by default spring sec prevents any header that has frame options
        //since it's a security issue but disable it here since h2 console uses it
        //don't use in production code
//            http.headers().frameOptions().disable();

        return http.build();


        //end here

    }


    //Non-prod user creation with roles. Better option than applications.properties
       /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("12345").roles("USER")
                .and()
                .withUser("admin").password("54321").roles("USER", "ADMIN")
                .and().passwordEncoder(NoOpPasswordEncoder.getInstance()); <- no pw encryption
    }*/


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
