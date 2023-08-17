package com.betsol.kanbanboardTask.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.betsol.kanbanboardTask.Enum.UserResponseMessages;
import com.betsol.kanbanboardTask.dao.UserDao;
import com.betsol.kanbanboardTask.entity.Tbluser;
import com.betsol.kanbanboardTask.utilities.Utilities;

@Service
public class UserService {
	
	@Autowired
	public UserDao userDao;
	
	public boolean existsById(Long id) {
		return userDao.existsById(id);
	}
	
	public List<Tbluser> getAllUsers() {
		ArrayList<Tbluser> users = new ArrayList<>();
		List<Tbluser> allUsers = userDao.findAll();
		for(Tbluser user: allUsers)
			if (user.getIsDeleted() == 0 && user.getIsAdmin() == 0) users.add(user);
		return users;
	}

	public Tbluser login(String username, String password) {
		Long id = Long.MIN_VALUE;
		Tbluser user = new Tbluser();
		if (userDao.existsByUsername(username)) {
			if (userDao.existsByPassword(password)) id = userDao.findByUsername(username).getId();
			else id = Long.parseLong("-2");
		} else id = Long.parseLong("-1");
		
		if (id > 0) {
			if (findById(id).getIsAdmin() == 1) {
				user.setOutputCode(UserResponseMessages.USER_NOT_FOUND.getAuthResponseMessage());
			} else {
				user = findById(id);
				user.setOutputCode(UserResponseMessages.LOGIN_SUCCESS.getAuthResponseMessage());
			}
		} else if (id == -1) user.setOutputCode(UserResponseMessages.USER_NOT_FOUND.getAuthResponseMessage());
		else if (id == -2) user.setOutputCode(UserResponseMessages.LOGIN_PASSWORD.getAuthResponseMessage());
		else user.setOutputCode(UserResponseMessages.OPERATION_FAILED.getAuthResponseMessage());
		
		return user;
	}
	
	public Tbluser adminLogin(String username, String password) {
		Long id = Long.MIN_VALUE;
		Tbluser user = new Tbluser();
		if (userDao.existsByUsername(username)) {
			if (userDao.existsByPassword(password)) id = userDao.findByUsername(username).getId();
			else id = Long.parseLong("-2");
		} else id = Long.parseLong("-1");
		
		if (id > 0) {
			if (findById(id).getIsAdmin() == 0) {
				user.setOutputCode(UserResponseMessages.USER_NOT_FOUND.getAuthResponseMessage());
			} else {
				user = findById(id);
				user.setOutputCode(UserResponseMessages.LOGIN_SUCCESS.getAuthResponseMessage());
			}
		} else if (id == -1) user.setOutputCode(UserResponseMessages.USER_NOT_FOUND.getAuthResponseMessage());
		else if (id == -2) user.setOutputCode(UserResponseMessages.LOGIN_PASSWORD.getAuthResponseMessage());
		else user.setOutputCode(UserResponseMessages.OPERATION_FAILED.getAuthResponseMessage());
		
		return user;
	}
	
	public Tbluser findById(Long id) {
		Optional<Tbluser> optUser =  userDao.findById(id);
		return optUser.get();
	}
	
	public Tbluser findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	public Tbluser findByPassword(String password) {
		return userDao.findByPassword(password);
	}
	
	public Tbluser findByUserId(String userId) {
		Optional<Tbluser> optUser =  userDao.findByUserId(userId);
		return optUser.get();
	}

	public Tbluser register(Tbluser user) {
		if (userDao.existsByUsername(user.getUsername())) {
			user.setOutputCode(UserResponseMessages.USER_ALREADY_AVAILABLE.getAuthResponseMessage());
			return user;
		} else {
			user.setUserId(Utilities.generateRandomId(30));
			LocalDate createdDate = LocalDate.now();
			user.setCreatedAt(createdDate.toString());
			return userDao.save(user);
		}
	}
	
	public Tbluser updateUser(Tbluser user) {
		return userDao.save(user);
	}
	
	public Tbluser softDeleteUser(Long id) {
		Tbluser user = new Tbluser();
		if (existsById(id)) {
			user = findById(id);
			user.setIsDeleted(1);
			userDao.save(user);
			if (user.getIsDeleted() == 1) user.setOutputCode(UserResponseMessages.USER_MOVED_TO_RECYCLEBIN.getAuthResponseMessage());
			else user.setOutputCode(UserResponseMessages.OPERATION_FAILED.getAuthResponseMessage());
		} else user.setOutputCode(UserResponseMessages.USER_NOT_FOUND.getAuthResponseMessage());
		return user;
		
	}
	
	public Tbluser recoverUserById(Long id) {
		Tbluser user = new Tbluser();
		if (existsById(id)) {
			user = findById(id);
			user.setIsDeleted(0);
			userDao.save(user);
			if (user.getIsDeleted() == 0) user.setOutputCode(UserResponseMessages.USER_RESTORED.getAuthResponseMessage());
			else user.setOutputCode(UserResponseMessages.OPERATION_FAILED.getAuthResponseMessage());
		} else user.setOutputCode(UserResponseMessages.USER_NOT_FOUND.getAuthResponseMessage());
		return user;
	}

	public Tbluser deleteUser(Long id) {
		Tbluser user = new Tbluser();
		if (existsById(id)) {
			user = findById(id);
			userDao.delete(user);
			if (existsById(user.getId())) user.setOutputCode(UserResponseMessages.OPERATION_FAILED.getAuthResponseMessage());
			else {
				user = new Tbluser();
				user.setOutputCode(UserResponseMessages.USER_DELETED.getAuthResponseMessage());
			}
		} else user.setOutputCode(UserResponseMessages.USER_NOT_FOUND.getAuthResponseMessage());
		return user;
	}

	public Tbluser getUserById(String userId) {
		Tbluser user = new Tbluser();
		if (userDao.existsByUserId(userId)) {
			user = findByUserId(userId);
		} else user.setOutputCode(UserResponseMessages.USER_NOT_FOUND.getAuthResponseMessage());
		return user;
	}

}
