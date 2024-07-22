package edu.icet.crm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.enitity.EmployeeEntity;
import edu.icet.crm.model.Employee;
import edu.icet.crm.repository.EmployeeRepository;
import edu.icet.crm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public List<Employee> getAllEmployees() {
        Iterable<EmployeeEntity> all = employeeRepository.findAll();

        List<Employee> employeeList = new ArrayList<>();
        all.forEach(employeeEntity -> {
            Employee employee = convertToModel(employeeEntity);
            employeeList.add(employee);
        });
        return employeeList;
    }
    @Override
    public Employee getEmployeeById(Long id) {
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(id);
        if (optionalEmployeeEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with ID " + id + " not found");
        }
        return convertToModel(optionalEmployeeEntity.get());
    }

    @Override
    public void addEmployee(Employee employee) {
        if (!employee.equals(null)){
            EmployeeEntity employeeEntity = convertToEntity(employee);
            employeeRepository.save(employeeEntity);
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        if(employeeRepository.existsById(employee.getId())){
            EmployeeEntity employeeEntity = convertToEntity(employee);
            employeeRepository.save(employeeEntity);
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        if(employeeRepository.existsById(id)){

            employeeRepository.deleteById(id);
        }
    }

    private EmployeeEntity convertToEntity(Employee employee) {
        return mapper.convertValue(employee, EmployeeEntity.class);
    }

    private Employee convertToModel(EmployeeEntity employeeEntity) {
        return mapper.convertValue(employeeEntity, Employee.class);
    }
}
