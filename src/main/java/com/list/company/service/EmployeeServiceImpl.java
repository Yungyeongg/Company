package com.list.company.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.list.company.DTO.EmployeeDTO;
import com.list.company.model.Employee;
import com.list.company.model.Users;
import com.list.company.repository.EmployeeRepository;
import com.list.company.repository.UsersRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final UsersRepository usersRepository;
	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, UsersRepository usersRepository) {
		this.employeeRepository = employeeRepository;
		this.usersRepository = usersRepository;
	}
	
	public void saveEmployee(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		Users users = new Users();
		
		employee.setDepartment(employeeDTO.getDepartment());
		employee.setRights(employeeDTO.getRights());
		employee.setName(employeeDTO.getName());
		employee.setEmail(employeeDTO.getEmail());
		employee.setPhone(employeeDTO.getPhone());
		
		users.setUserId(employeeDTO.getId());
		
		String encryptedPassword = passwordEncoder.encode(employeeDTO.getPassword());
		users.setPassword(encryptedPassword);
		
		employeeRepository.save(employee);
		usersRepository.save(users);
	}
}
