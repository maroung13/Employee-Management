package com.example.employeemanagement.service;

import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees(String sort, String order){
        List<Employee> employeesSortedByName ;
        if(Objects.equals(order, "asc")) employeesSortedByName = employeeRepository.findAll(Sort.by(sort).ascending());
        else if(Objects.equals(order, "desc")) employeesSortedByName = employeeRepository.findAll(Sort.by(sort).descending());
        else throw new IllegalStateException("Please choose a valid order method");
        return employeesSortedByName;
    }

    public List<Employee> getEmployeesByDepartment(Department department) {
        return employeeRepository.findEmployeesByDepartment(department);
    }

    public Employee createEmployee(Employee employee){
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(employee.getEmail());
        if(employeeOptional.isPresent()) throw new IllegalStateException("The email is already taken");
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(Long employeeId, Employee employeeRequest){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException("employee with id " + employeeId + " does not exists"));

        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(employeeRequest.getEmail());
        if(employeeOptional.isPresent() && !employeeOptional.get().equals(employee)) throw new IllegalStateException("The email is already taken");

        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setDateOfBirth(employeeRequest.getDateOfBirth());
        employee.setDepartment(employeeRequest.getDepartment());
        return employee;
    }

    public void deleteEmployee(Long employeeId){
        boolean exists = employeeRepository.existsById(employeeId);
        if(!exists){
            throw new IllegalStateException("employee with id " + employeeId + " does not exists");
        }
        employeeRepository.deleteById(employeeId);
    }
}
