package com.list.company.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.list.company.model.Users;
import com.list.company.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {
	
	private final UsersRepository usersRepository;
	
	public UsersServiceImpl(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	
	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public boolean authenticate(String userId, String password) {
        Optional<Users> users = usersRepository.findByUserId(userId);
        return users.isPresent() && passwordEncoder.matches(password, users.get().getPassword());//get():Optionalオブジェクトで値を出す時method
    }								// matches methodは入力したパスワードをハッシュ化してデータベースに暗号化されたパスワードと比較する
}
