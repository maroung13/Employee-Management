package com.example.employeemanagement.controller;

import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees(@RequestParam(defaultValue = "id") String sort,
                                                       @RequestParam(defaultValue = "asc") String order){
        return ResponseEntity.ok(employeeService.getEmployees(sort, order));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@RequestParam Department department) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(department));
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employee));
    }

    @PutMapping("/{employee-id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("employee-id") Long employeeId, @RequestBody Employee employee){
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employee));
    }

    @DeleteMapping("/{employee-id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("employee-id") Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
