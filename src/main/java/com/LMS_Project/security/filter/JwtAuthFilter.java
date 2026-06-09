package com.LMS_Project.security.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.LMS_Project.security.service.CustomUserDetailsService;
import com.LMS_Project.security.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private static final Logger logger =
            LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil,
                         CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		  // Step 1: Read the Authorization header
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        
     // Step 2: Check if header has "Bearer " token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwtToken);
                logger.debug("JWT token found for user: {}", username);
            } catch (Exception e) {
                logger.error("Unable to extract JWT token: {}",
                             e.getMessage());
            }
        } else {
            logger.debug("No JWT token in request: {}",
                         request.getRequestURI());
        }
        
     // Step 3: Validate token and set authentication
        if (username != null
                && SecurityContextHolder.getContext()
                                        .getAuthentication() == null) {
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null,
                                userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));
                SecurityContextHolder.getContext()
                                     .setAuthentication(authToken);
                logger.info("User authenticated: {}", username);
            }
        }

        // Step 4: Continue the filter chain
        filterChain.doFilter(request, response);
    
		
	}
    
    
}
