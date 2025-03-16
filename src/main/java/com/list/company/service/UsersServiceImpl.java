package com.list.company.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.list.company.model.Users;
import com.list.company.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {
	
	private final UsersRepository usersRepository;
	
	public UsersServiceImpl(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	
	public boolean authenticate(String userId, String password) {
        Optional<Users> users = usersRepository.findByUserId(userId);
        return users.isPresent() && users.get().getPassword().equals(password);//get():Optionalオブジェクトで値を出す時method
    }
}
