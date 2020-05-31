package com.userfront.service;

import java.util.List;
import java.util.Set;

import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;

public interface UserService {
	User FindByUsername(String username);
	
	User FindByEmail(String email);
	
	boolean checkUserExists(String username, String email);
	
	boolean checkUsernameExists(String username);
	
	boolean checkEmailexists(String email);
	
	void save (User user);
	
	User createUser(User user, Set<UserRole> userRoles);

	User findByUsername(String name);
	
	User saveUser(User user);
	
	List<User> findUserList();

	void enableUser (String username);

	void disableUser (String username);

}
