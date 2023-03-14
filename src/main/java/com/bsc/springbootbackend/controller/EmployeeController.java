package com.bsc.springbootbackend.controller;

import com.bsc.springbootbackend.exception.ResourceNotFoundException;
import com.bsc.springbootbackend.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bsc.springbootbackend.repository.EmployeeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired // inject TutorialRepository bean to local variable.
    private EmployeeRepository employeeRepository;

    //get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //Create new EMployee
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    //get employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id Not Found:" + id));
        return ResponseEntity.ok(employee);
    }

    //Update EMployee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeUpdates) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id Not Found:" + id));
        employee.setActive(employeeUpdates.isActive());
        employee.setFirstName(employeeUpdates.getFirstName());
        employee.setLastName(employeeUpdates.getLastName());
        employee.setEmail(employeeUpdates.getEmail());
        Employee updatedEmployee = employeeRepository.save(employee);

        return ResponseEntity.ok(updatedEmployee);
    }

    //delete employee api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity <Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id Not Found:" + id));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted Successfully", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    //search employees by string
    @GetMapping("/employees/search")
    public List<Employee> searchEmployeesByString(@RequestParam String query) {
        query = query.toLowerCase();
        System.out.println("Search for: " + query);
        return employeeRepository.findEmployeeBySearch(query);
    }
}
