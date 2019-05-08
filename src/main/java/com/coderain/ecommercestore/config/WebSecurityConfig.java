package com.coderain.ecommercestore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.coderain.ecommercestore.security.jwt.JwtAuthenticationEntryPoint;
import com.coderain.ecommercestore.security.jwt.JwtAuthorizationTokenFilter;
import com.coderain.ecommercestore.security.service.JwtUserDetailsService;

/**
 * @author zhora
 * @verson 0.1
 * 
 * */


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtUserDetailsService jwtUserDetailsService;
	
	private final JwtAuthenticationEntryPoint unauthorizedHandler;

	private final JwtAuthorizationTokenFilter authorizationTokenFilter;
	
	@Value("${jwt.header}")
	private String tokenHeader;
	
	@Value("${jwt.route.authentication.path}")
	private String authenticationPath;
	
	@Autowired
	public WebSecurityConfig(
			final JwtUserDetailsService jwtUserDetailsService,
			final JwtAuthenticationEntryPoint unauthorizedHandler, 
			final JwtAuthorizationTokenFilter authorizationTokenFilter) {
		super();
		this.authorizationTokenFilter = authorizationTokenFilter;
		this.jwtUserDetailsService = jwtUserDetailsService;
		this.unauthorizedHandler = unauthorizedHandler;
	}

	@Override
	public void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder.userDetailsService(jwtUserDetailsService).passwordEncoder(encoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	private final String[] PUBLIC_URL = { "/api/auth/**", "/api/users/signup", "/api/users/get-all-users" };

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(
				HttpMethod.POST, authenticationPath)
		.and().ignoring().antMatchers(
				"/",
				"/*.html",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js"
				);
	}	

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().antMatchers(PUBLIC_URL).permitAll().anyRequest()
				.authenticated().and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(authorizationTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
