package com.epam.controller;

import com.epam.dto.EmployeeDTO;
import com.epam.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> add(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employeeDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getEmployees());
    }
}
