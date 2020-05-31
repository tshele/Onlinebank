package com.userfront.service.UserServiceImpl;


import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.userfront.Dao.RoleDao;
import com.userfront.Dao.UserDao;
import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;
import com.userfront.service.AccountService;
import com.userfront.service.UserService;

import ch.qos.logback.classic.Logger;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	private static final org.jboss.logging.Logger LOG = LoggerFactory.logger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AccountService accountService;
	
	

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
    
   
    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userDao.findByUsername(user.getUsername());

        if (localUser != null) {
            LOG.info("User with username {} already exist. Nothing will be done. ");
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            for (UserRole ur : userRoles) {
                roleDao.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);

            user.setPrimaryAccount(accountService.createPrimaryAccount());
            user.setSavingsAccount(accountService.createSavingsAccount());

            localUser = userDao.save(user);
        }

        return localUser;
    }
    
    
    public boolean checkUserExists(String username, String email){
        if (checkUsernameExists(username) || checkEmailExists(username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameExists(String username) {
        if (null != findByUsername(username)) {
            return true;
        }

        return false;
    }
    
    public boolean checkEmailExists(String email) {
        if (null != findByEmail(email)) {
            return true;
        }

        return false;
    }
    
    public void save(User user) {
    	userDao.save(user);
    }
    
    public void enableUser (String username) {
        User user = findByUsername(username);
        user.setEnabled(true);
        userDao.save(user);
    }
    
    public void disableUser (String username) {
        User user = findByUsername(username);
        user.setEnabled(false);
        System.out.println(user.isEnabled());
        userDao.save(user);
        System.out.println(username + " is disabled.");
    }


	@Override
	public User FindByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User FindByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkEmailexists(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findUserList() {
		// TODO Auto-generated method stub
		return null;
	}

	
	}


  



