package com.epam.service;

import com.epam.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployee(Integer employeeId);

    EmployeeDTO updateEmployee(Integer employeeId, EmployeeDTO employeeDTO);

    void deleteEmployee(Integer employeeId);
}
