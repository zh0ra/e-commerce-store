package com.coderain.ecommercestore.security.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coderain.ecommercestore.security.jwt.JwtAuthenticationRequest;
import com.coderain.ecommercestore.security.jwt.JwtTokenUtil;
import com.coderain.ecommercestore.security.jwt.JwtUser;
import com.coderain.ecommercestore.services.JwtAuthenticationResponse;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
//@RequestMapping("/api/auth")
public class AuthenticationRestController {

	@Value("${jwt.header}")
	private String tokenHeader;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthenticationManager authenticaitonManager;

	@Autowired
	@Qualifier("jwtUserDetailService")
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder encoder;

	@RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticateToken(final @Valid @RequestBody JwtAuthenticationRequest authenticationRequest) {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}

	@RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.POST)
	public ResponseEntity<?> refreshAuthenticationToken(final HttpServletRequest request) {
		
		final String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);
		final String username = jwtTokenUtil.getUsernameFromToken(token);
		
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
		
		if(jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			String refreshToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new JwtAuthenticationResponse(refreshToken));
		}else {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	private void authenticate(String username, String password) {
		
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		
		try {
			this.authenticaitonManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}catch(DisabledException e ) {
			throw new AuthenticationException("User is disabled!", e);
		}catch(BadCredentialsException e) {
			throw new AuthenticationException("Bad credentials!", e);
		}
	}

}
