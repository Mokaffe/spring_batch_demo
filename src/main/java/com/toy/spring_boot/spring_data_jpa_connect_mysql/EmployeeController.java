package com.toy.spring_boot.spring_data_jpa_connect_mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping("queryAll")
    @ResponseBody
    public List<Employee> queryAll() {
        List<Employee> list = new ArrayList<Employee>();
        list = employeeRepository.findAll();
        return list;
    }

    @RequestMapping("hello")
    @ResponseBody
    public String hello(){
        return "hello, world";
    }
}
