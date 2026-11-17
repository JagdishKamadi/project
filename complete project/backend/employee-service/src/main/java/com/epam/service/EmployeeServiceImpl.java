package com.epam.service;

import com.epam.dto.AddressDTO;
import com.epam.dto.EmployeeDTO;
import com.epam.exception.EmployeeNotFoundException;
import com.epam.model.Address;
import com.epam.model.Employee;
import com.epam.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

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

    @Override
    public EmployeeDTO updateEmployee(Integer employeeId, EmployeeDTO employeeDTO) {
        Objects.requireNonNull(employeeDTO, "employeeDTO must not be null");
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        updateEmployeeFields(employee, employeeDTO);
        updateAddressFields(employee, employeeDTO.getAddress());

        Employee savedEmployee = employeeRepository.save(employee);
        return objectMapper.convertValue(savedEmployee, EmployeeDTO.class);
    }

    private void updateEmployeeFields(Employee employee, EmployeeDTO employeeDTO) {
        copyIfNotBlank(employeeDTO.getFirstName(), employee::setFirstName);
        copyIfNotBlank(employeeDTO.getLastName(), employee::setLastName);
        copyIfNotBlank(employeeDTO.getEmail(), employee::setEmail);
        if (employeeDTO.getAge() != null) {
            employee.setAge(employeeDTO.getAge());
        }
    }

    private void updateAddressFields(Employee employee, AddressDTO addressDTO) {
        if (addressDTO == null) {
            return;
        }
        Address address = Optional.ofNullable(employee.getAddress()).orElseGet(Address::new);
        employee.setAddress(address);

        copyIfNotBlank(addressDTO.getHouseNumber(), address::setHouseNumber);
        copyIfNotBlank(addressDTO.getBuildingName(), address::setBuildingName);
        copyIfNotBlank(addressDTO.getStreet(), address::setStreet);
        copyIfNotBlank(addressDTO.getLandmark(), address::setLandmark);
        copyIfNotBlank(addressDTO.getLocality(), address::setLocality);
        copyIfNotBlank(addressDTO.getVillage(), address::setVillage);
        copyIfNotBlank(addressDTO.getCity(), address::setCity);
        copyIfNotBlank(addressDTO.getDistrict(), address::setDistrict);
        copyIfNotBlank(addressDTO.getState(), address::setState);
        copyIfNotBlank(addressDTO.getCountry(), address::setCountry);
        copyIfNotBlank(addressDTO.getPincode(), address::setPincode);
        copyIfNotBlank(addressDTO.getAddressType(), address::setAddressType);
    }

    private void copyIfNotBlank(String value, Consumer<String> setter) {
        if (isNotBlank(value)) {
            setter.accept(value);
        }
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }
}
