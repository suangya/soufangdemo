package com.soufang.soufangdemo.base.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soufang.soufangdemo.base.ApiResponse;
import com.soufang.soufangdemo.base.Status;
import com.soufang.soufangdemo.entity.Role;
import com.soufang.soufangdemo.entity.User;
import com.soufang.soufangdemo.repository.RoleRepository;
import com.soufang.soufangdemo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ObjectMapper mapper;
    private SmsCodeAuthenticationFilter filter;

    public WebSecurityConfig(UserRepository userRepository, RoleRepository roleRepository, ObjectMapper mapper, SmsCodeAuthenticationFilter filter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.authorizeHttpRequests(registry ->
                        registry.antMatchers("/api/users/**").authenticated()
                                .anyRequest().permitAll())
                .userDetailsService(getUserDetailsService())
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                                .accessDeniedHandler(accessDeniedHandler()))
                .formLogin(configurer ->
                        configurer
                                .loginProcessingUrl("/api/users/login")
                                .successHandler(getAuthenticationSuccessHandler())
                                .failureHandler(getFailureHandler()))
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return (request, response, accessDeniedException) -> {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(mapper.writeValueAsString(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase())));
            response.getWriter().flush();
        };
    }

    public AuthenticationSuccessHandler getAuthenticationSuccessHandler(){
        return (request, response, authentication) -> {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mapper.writeValueAsString(ApiResponse.success()));
            response.getWriter().flush();
        };
    }

    public AuthenticationFailureHandler getFailureHandler(){
        return (request, response, exception) -> {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(mapper.writeValueAsString(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase())));
            response.getWriter().flush();
        };
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return identity -> {
            User user;
            if ((user=userRepository.findByName(identity))==null&& (user = userRepository.findByEmail(identity)) == null && (user = userRepository.findByPhoneNumber(identity)) == null) {
                throw new UsernameNotFoundException(Status.ERROR_USERNAME_OR_PASSWORD.getMessage());
            }
            List<Role> roles = roleRepository.findAllByUserId(user.getId());
            return new SecurityUser(user,roles);
        };
    }
    /*identity -> {
        com.soufang.soufangdemo.entity.User user;
        if ((user = userRepository.findByName(identity)) == null && (user = userRepository.findByEmail(identity)) == null && (user = userRepository.findByPhoneNumber(identity)) == null) {
            throw new UsernameNotFoundException(Status.ERROR_USERNAME_OR_PASSWORD.getMessage());
        }
        List<Role> roles = roleRepository.findAllByUserId(user.getId());
        return new SecurityUser(user,roles);
    };*/

}
