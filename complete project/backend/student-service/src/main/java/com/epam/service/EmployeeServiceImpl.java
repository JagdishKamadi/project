package com.epam.service;

import com.epam.dto.EmployeeDTO;
import com.epam.model.Employee;
import com.epam.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ObjectMapper objectMapper) {
        this.employeeRepository = employeeRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = objectMapper.convertValue(employeeDTO, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return objectMapper.convertValue(savedEmployee, EmployeeDTO.class);
    }
}
