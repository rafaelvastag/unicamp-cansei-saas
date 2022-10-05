package com.unicamp.inf332.cansei.application.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.unicamp.inf332.cansei.crosscutting.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
