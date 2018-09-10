package com.toy.spring_boot.spring_data_jpa_connect_mysql;


import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String>{
}
