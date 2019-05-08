package com.coderain.ecommercestore.security.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.coderain.ecommercestore.domain.Authority;
import com.coderain.ecommercestore.domain.User;

public class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static JwtUser create(final User user) {
		return new JwtUser(
				user.getId(),
				user.getUsername(),
				user.getFirstName(),
				user.getEmail(),
				user.getLastName(),
				user.getPassword(),
				mapToGrantedAuthorities(user.getAuthorities()),
				user.isEnabled(),
				user.getLastPasswordResetDate(),
				user.isAccountNonExpired(),
				user.isAccountNonLocked(),
				user.isCredentialsNonExpired());
	}
	
	private static  List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities){
		
		return authorities.stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getName().name())
						).collect(Collectors.toList());
	}
}
