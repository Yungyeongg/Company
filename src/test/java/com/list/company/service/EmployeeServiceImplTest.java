package com.list.company.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.ArgumentMatchers.any;

import com.list.company.DTO.EmployeeDTO;
import com.list.company.model.Employee;
import com.list.company.model.Users;
import com.list.company.repository.EmployeeRepository;
import com.list.company.repository.UsersRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@Mock
	private UsersRepository usersRepository;
	
	@Mock
	private BCryptPasswordEncoder passwordEncoder; 
	
	private EmployeeDTO mockEmployeeDTO;
	
	private Employee mockEmployee;
	private Users mockUsers;
	
	@BeforeEach
	void setUp() {
		mockEmployeeDTO= new EmployeeDTO();
		mockEmployeeDTO.setDepartment("営業部");
		mockEmployeeDTO.setRights("管理者");
		mockEmployeeDTO.setName("ひらがなカタカナ漢字");
		mockEmployeeDTO.setEmail("abcd@example.com");
		mockEmployeeDTO.setPhone("1234567890");
		mockEmployeeDTO.setId("id1234");
		mockEmployeeDTO.setPassword("encryptedPassword123#");
	}
	
	@Test
	void saveEmployeeTest() {
   	
        employeeServiceImpl.saveEmployee(mockEmployeeDTO);
        
        mockEmployee = new Employee();
        mockUsers = new Users();
        
        mockEmployee.getDepartment();
        mockEmployee.getRights();
        mockEmployee.getName();
		mockEmployee.getEmail();
		mockEmployee.getPhone();
		mockUsers.getUserId();
		mockUsers.getPassword();
		
          //verify(employeeRepository).save(mockEmployee);
          //verify(usersRepository).save(mockUsers);
	}       
}
