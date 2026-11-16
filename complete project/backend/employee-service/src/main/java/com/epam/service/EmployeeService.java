package com.epam.service;

import com.epam.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO save(EmployeeDTO employeeDTO);
    List<EmployeeDTO> getEmployees();
}
