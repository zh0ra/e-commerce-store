package com.coderain.ecommercestore.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
/**
 * @author zhora
 * @version 0.1
 * 
 * */

@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationTokenFilter.class);

	private JwtTokenUtil jwtTokenUtil;
	private UserDetailsService userDetailsService;
	private String tokenHeader;

	public JwtAuthorizationTokenFilter(@Qualifier("jwtUserDetailsSerivce") UserDetailsService userDetailsService,
			JwtTokenUtil jwtTokenUtil, @Value("${jwt.header}") String tokenHeader) {
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.tokenHeader = tokenHeader;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain)
			throws ServletException, IOException {
		logger.debug("processing authentication for '{}", request.getRequestURL());

		final String requestHeader = request.getHeader((this.tokenHeader));

		String username = null;
		String authToken = null;

		// request header conditions of filtering
		if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
			authToken = requestHeader.substring(7);
			try {
				username = this.jwtTokenUtil.getUsernameFromToken(authToken);
			} catch (IllegalArgumentException e) {
				logger.error("an error occured during getting username form token.", e);
			} catch (ExpiredJwtException e) {
				logger.warn("the token is expired and not valid anymore", e);
			}
		} else {
			logger.warn("couldn't find barier string, will ignore the header");
		}

		logger.debug("checking authentication for user '{}'", username);

		// check the SecredContextHolder is not empty
		if (requestHeader != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			logger.debug("security context was null");
			UserDetails userDetails;
			try {
				userDetails = userDetailsService.loadUserByUsername(username);
			} catch(UsernameNotFoundException e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
				return;
			}
			
			if (jwtTokenUtil.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
	
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				logger.info("authorized user '{}', setting securty context", username);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}
}
