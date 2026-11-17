package com.epam.service;

import com.epam.dto.EmployeeDTO;
import com.epam.exception.EmployeeNotFoundException;
import com.epam.model.Employee;
import com.epam.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ObjectMapper objectMapper) {
        this.employeeRepository = employeeRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Objects.requireNonNull(employeeDTO, "employeeDTO must not be null");
        Employee employee = objectMapper.convertValue(employeeDTO, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return objectMapper.convertValue(savedEmployee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> objectMapper.convertValue(employee, EmployeeDTO.class))
                .toList();
    }

    @Override
    public EmployeeDTO getEmployee(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        return objectMapper.convertValue(employee, EmployeeDTO.class);
    }
}
