package com.application.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter{

	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String reqUrl = request.getRequestURI();
		
		if(reqUrl.contains("/registrationDetails") || reqUrl.contains("/login")) {
			filterChain.doFilter(request, response);
			return;
		}

		if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
			String jwt = authHeader.substring(7);

			if (jwt == null && jwt.isBlank()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Jwt token in Bearer Token ");
			} else {
				try {
					String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);
					UserDetails userDetails = myUserDetailsService.loadUserByUsername(email);

					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email,
							userDetails.getPassword(), userDetails.getAuthorities());

					if (SecurityContextHolder.getContext().getAuthentication() == null) {
						SecurityContextHolder.getContext().setAuthentication(authToken);
						filterChain.doFilter(request, response);
					}
				} catch (JWTVerificationException exc) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid jwt Token");
				}
			}
		}

	}

}
