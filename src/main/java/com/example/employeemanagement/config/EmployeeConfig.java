package com.example.employeemanagement.config;

import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class EmployeeConfig{

    @Bean
    CommandLineRunner commandLineRunner(EmployeeRepository repository){
        return args -> {
            Employee johnSmith = new Employee(
                    "John",
                    "Smith",
                    "johnsmith@gmail.com",
                    LocalDate.of(2000, Month.SEPTEMBER, 12),
                    Department.DEVELOPMENT
            );
            Employee johnDoe = new Employee(
                    "John",
                    "Doe",
                    "johndoe@hotmail.com",
                    LocalDate.of(1990, Month.JANUARY, 10),
                    Department.HR
            );
            repository.saveAll(List.of(johnSmith,johnDoe));
        };
    }
}

