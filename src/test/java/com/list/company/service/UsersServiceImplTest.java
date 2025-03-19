package com.list.company.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.list.company.model.Users;
import com.list.company.repository.UsersRepository;

public class UsersServiceImplTest {
	
	// UsersRepositoryをMockingする
	@Mock
    private UsersRepository usersRepository;  
	
	// UsersServiceImplにMockingされたUsersRepositoryを収入する
	@InjectMocks
	private UsersServiceImpl usersService; 
	
	//　Usersオブジェクト
	private Users mockUser;
	
	//　パスワード暗号化
	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	//　Mokitoの@Mockと@InjectMocksを初期化する
	@BeforeEach
    public void setUp() {
        
		MockitoAnnotations.openMocks(this); 
        
        // テスト用ユーザーオブジェクト生成
        mockUser = new Users(); 
        String encodedPassword = passwordEncoder.encode("testPassword");
        mockUser.setUserId("testUser");
        mockUser.setPassword(encodedPassword);
    }
	
	@Test
    public void Authenticate成功テスト() {
        // given: usersRepositoryが特定userIdを探す際にmockUserを返還するように設定
        when(usersRepository.findByUserId("testUser")).thenReturn(Optional.of(mockUser));

        // when: authenticate メソッド呼び出し
        boolean result = usersService.authenticate("testUser", "testPassword");

        // then: 認証が成功的に行った検証
        assertTrue(result);
    }

    @Test
    public void AuthenticateWrongPassword失敗テスト() {
        // given: usersRepositoryが特定userIdを探す際にmockUserを返還するように設定
        when(usersRepository.findByUserId("testUser")).thenReturn(Optional.of(mockUser));

        // when: 間違いパスワードで authenticate メソッド呼び出し
        boolean result = usersService.authenticate("testUser", "wrongPassword");

        // then: 認証が成功的に行った検証
        assertFalse(result);
    }

    @Test
    public void AuthenticateWorongId失敗テスト() {
        // given: usersRepositoryが特定userIdを探せないように設定
        when(usersRepository.findByUserId("nonExistentUser")).thenReturn(Optional.empty());

        // when: 存在しないIDでauthenticate　メソッド呼び出し
        boolean result = usersService.authenticate("nonExistentUser", "testPassword");

        // then: 認証が失敗した検証
        assertFalse(result);
    }
}
