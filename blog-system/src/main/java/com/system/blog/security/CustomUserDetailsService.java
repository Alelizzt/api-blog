package com.system.blog.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.system.blog.model.Role;
import com.system.blog.model.BlogUser;
import com.system.blog.repository.BlogUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private BlogUserRepository blogUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		BlogUser user = blogUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with that username or email :"+ usernameOrEmail));
		
		return new User(user.getEmail(), user.getPassword(), mapRoles(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRoles(Set<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
