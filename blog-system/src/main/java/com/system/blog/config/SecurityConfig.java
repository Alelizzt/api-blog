package com.system.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.system.blog.security.CustomUserDetailsService;
import com.system.blog.security.JwtAuthenticationEntryPoint;
import com.system.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf  -> csrf.disable());
		http.exceptionHandling((exception)-> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests( (authz) -> authz
				.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
				.requestMatchers("/api/auth/**").permitAll()
				.requestMatchers("/swagger-ui/**").permitAll()
				.requestMatchers("/v3/api-docs/**").permitAll()
				.anyRequest()
				.authenticated());
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	protected AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}

	/*
	 * @Bean protected InMemoryUserDetailsManager userDetailsService() { UserDetails
	 * admin = User.builder() .username("admin")
	 * .password(passwordEncoder().encode("123456")) .roles("ADMIN","USER")
	 * .build(); UserDetails user = User.builder() .username("user")
	 * .password(passwordEncoder().encode("123456")) .roles("USER") .build(); return
	 * new InMemoryUserDetailsManager(admin, user); }
	 */

}
