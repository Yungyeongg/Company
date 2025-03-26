package com.list.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.list.company.model.Employee;
import com.list.company.model.Users;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
