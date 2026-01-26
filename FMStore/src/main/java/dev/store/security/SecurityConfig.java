package dev.store.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.store.controller.FoodController;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	 private final FoodController foodController;
		private JwtAuthFilter jwtFilter;
		public SecurityConfig(JwtAuthFilter jwtFilter, FoodController foodController) {
			this.jwtFilter=jwtFilter;
			this.foodController = foodController;
			
		}
		@Bean
		PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		@Bean
		SecurityFilterChain securityFilterChain(HttpSecurity http ){
			http
				.csrf(csrf->csrf.disable()).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth-> auth
						.requestMatchers("/auth/**").permitAll()						// login aperto
						.requestMatchers(HttpMethod.GET, "/api/foods/**").permitAll()	// GET tutti possono consultare
						.requestMatchers("/api/foods/**").hasRole("ADMIN")				// post,put,delete solo per admin 
						.anyRequest().authenticated()
				)	
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
				
			return http.build();
				
			
		}
}
