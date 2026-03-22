package dev.store.security;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import dev.store.services.JwtService;
import io.jsonwebtoken.Claims;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	public JwtAuthFilter(JwtService jwtService) {
		this.jwtService=jwtService;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader= request.getHeader("Authorization");
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			
			String token=authHeader.substring(7);
			Claims claim=jwtService.extractClaims(token);
			String username= claim.getSubject();
			String role= claim.get("role", String.class);
			
			List<GrantedAuthority> authorities =
			        List.of(new SimpleGrantedAuthority("ROLE_" + role));

			Authentication authentication =
			        new UsernamePasswordAuthenticationToken(username, null,authorities);
		    SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

}
