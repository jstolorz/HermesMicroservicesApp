package com.bluesoft.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    public WebSecurity(final Environment environment) {
        this.environment = environment;
    }


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"));
        http.headers().frameOptions().disable();}
}