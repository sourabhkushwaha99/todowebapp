package com.todo.app.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.todo.app.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
    private UserService userService;
	 
	
	 
  
	 
	 	@Bean
	     PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 	
	 	
	 	@Bean
	     DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userService);
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    } 
	 	 
	 	 
	 	 
	 	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	         auth.authenticationProvider(authenticationProvider());
	     }
	 	 
	 	 
	 	 @Bean
	      SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	         http
	             .authorizeHttpRequests(authz -> authz
	                 .requestMatchers("/register", "/login","/css/**","/image/**").permitAll()
	                 .anyRequest().authenticated() 
	             )
	             .formLogin(form -> form
	                 .loginPage("/login")
	                 .defaultSuccessUrl("/home", true)
	                 .permitAll()
	             )
	             .logout(logout -> logout
	                     .logoutUrl("/logout")
	                     .logoutSuccessUrl("/login?logout")
	                     .permitAll()
	                 )
	             .csrf(csrf -> csrf
	                     .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	                 );
	         return http.build();  
	     }
	 	 
	 	 
	 	@Bean 
	    AuthenticationFailureHandler authenticationFailureHandler() {
	        return new SimpleUrlAuthenticationFailureHandler("/login?error=true");
	    }

}
