package com.example.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6935668325036563783L;

	private UserCreds userCreds;
	
	public UserPrincipal(UserCreds userCreds) {
		this.userCreds = userCreds;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> auths = new ArrayList<>();
		for (String role : userCreds.getRoles()) {
			auths.add(new SimpleGrantedAuthority(role));
		}
		return auths;
	}

	@Override
	public String getPassword() {
		return userCreds.getPassword();
	}

	@Override
	public String getUsername() {
		return userCreds.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}