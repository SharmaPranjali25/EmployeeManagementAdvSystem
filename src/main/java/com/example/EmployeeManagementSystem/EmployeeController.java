//package com.example.EmployeeManagementSystem;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import com.example.EmployeeManagementSystem.dto.EmployeeRequestDTO;
//import com.example.EmployeeManagementSystem.dto.EmployeeResponseDTO;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/employees")
//public class EmployeeController {
//
//    @Autowired
//    private EmployeeService service;
//
//    // Add Employee
//    @PostMapping("/add")
//    public EmployeeResponseDTO addEmployee(@RequestBody EmployeeRequestDTO requestDTO) {
//        Employee employee = new Employee();
//        employee.setName(requestDTO.getName());
//        employee.setDepartment(requestDTO.getDepartment());
//        employee.setSalary(requestDTO.getSalary());
// Employee savedEmployee = service.saveEmployee(employee);
//        EmployeeResponseDTO savedEmployee = service.saveEmployee(requestDTO);
//
//
//        return new EmployeeResponseDTO(savedEmployee.getId(), savedEmployee.getName(), savedEmployee.getDepartment());
//    }
//
//    // Get All Employees
//    @GetMapping("/all")
//    public List<EmployeeResponseDTO> getAllEmployees() {
//        return service.getAllEmployees().stream()
//                .map(emp -> new EmployeeResponseDTO(emp.getId(), emp.getName(), emp.getDepartment()))
//                .collect(Collectors.toList());
//    }
//
//    // Get Employee by ID
//    @GetMapping("/{id}")
//    public EmployeeResponseDTO getEmployeeById(@PathVariable Long id) {
//        EmployeeResponseDTO emp = service.getEmployeeById(id)
//                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
//
//        return new EmployeeResponseDTO(emp.getId(), emp.getName(), emp.getDepartment());
//    }
//
//    // Update Employee
//    @PutMapping("/update/{id}")
//    public EmployeeResponseDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDTO requestDTO) {
//        EmployeeRequestDTO updatedEmployee = new EmployeeRequestDTO();
//        updatedEmployee.setName(requestDTO.getName());
//        updatedEmployee.setDepartment(requestDTO.getDepartment());
//        updatedEmployee.setSalary(requestDTO.getSalary());
//
//        EmployeeResponseDTO emp = service.updateEmployee(id, updatedEmployee);
//
//        if (emp == null) {
//            throw new RuntimeException("Employee not found with id: " + id);
//        }
//
//        return new EmployeeResponseDTO(emp.getId(), emp.getName(), emp.getDepartment());
//    }
//
//    // Delete Employee
//    @DeleteMapping("/delete/{id}")
//    public String deleteEmployee(@PathVariable Long id) {
//        service.deleteEmployee(id);
//        return "Employee deleted successfully!";
//    }
//}

//WITH LOGGING


package com.example.EmployeeManagementSystem;
import com.example.EmployeeManagementSystem.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.example.EmployeeManagementSystem.dto.EmployeeResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService service;
    
    @Autowired
    private ModelMapper modelMapper;

    // Add Employee
    
//    @PostMapping("/add")
//    public EmployeeResponseDTO addEmployee(@RequestBody EmployeeRequestDTO requestDTO) {
//        log.info("POST /employees/add - Request to add employee: {}", requestDTO.getName());
//
//        EmployeeResponseDTO savedEmployee = service.saveEmployee(requestDTO);
//        log.debug("Employee saved successfully: {}", savedEmployee);
//
//        return new EmployeeResponseDTO(savedEmployee.getId(), savedEmployee.getName(), savedEmployee.getDepartment());
//    }
//WITH MODELMAPPER
    @PostMapping("/add")
    public EmployeeResponseDTO addEmployee(@RequestBody EmployeeRequestDTO requestDTO) {
        log.info("POST /employees/add - Request to add employee: {}", requestDTO.getName());

        var savedEmployeeEntity = service.saveEmployeeEntity(requestDTO);

        // Map Entity → DTO
        EmployeeResponseDTO savedEmployeeDTO = modelMapper.map(savedEmployeeEntity, EmployeeResponseDTO.class);

        log.debug("Employee saved successfully: {}", savedEmployeeDTO.getName());
        return savedEmployeeDTO;
    }
    
    // Get All Employees
//    @GetMapping("/all")
//    public List<EmployeeResponseDTO> getAllEmployees() {
//        log.info("GET /employees/all - Request to fetch all employees");
//
//        List<EmployeeResponseDTO> employees = service.getAllEmployees().stream()
//                .map(emp -> new EmployeeResponseDTO(emp.getId(), emp.getName(), emp.getDepartment()))
//                .collect(Collectors.toList());
//
//        log.debug("Total employees fetched: {}", employees.size());
//        return employees;
//    }
    @GetMapping("/all")
    public List<EmployeeResponseDTO> getAllEmployees() {
        log.info("GET /employees/all - Request to fetch all employees");

        List<EmployeeResponseDTO> employees = service.getAllEmployees().stream()
                .map(emp -> modelMapper.map(emp, EmployeeResponseDTO.class))
                .collect(Collectors.toList());

        log.debug("Total employees fetched: {}", employees.size());
        return employees;
    }
    

    // Get Employee by ID
//    @GetMapping("/{id}")
//    public EmployeeResponseDTO getEmployeeById(@PathVariable Long id) {
//        log.info("GET /employees/{} - Request to fetch employee by ID", id);
//
//        EmployeeResponseDTO emp = service.getEmployeeById(id)
//        	    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
//
//
//        log.debug("Employee found: {}", emp.getName()); 
//        return new EmployeeResponseDTO(emp.getId(), emp.getName(), emp.getDepartment());
//    }
    @GetMapping("/{id}")
    public EmployeeResponseDTO getEmployeeById(@PathVariable Long id) {
        log.info("GET /employees/{} - Request to fetch employee by ID", id);

        var employeeEntity = service.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        return modelMapper.map(employeeEntity, EmployeeResponseDTO.class);
    }

    // Update Employee
//    @PutMapping("/update/{id}")
//    public EmployeeResponseDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDTO requestDTO) {
//        log.info("PUT /employees/update/{} - Request to update employee: {}", id, requestDTO.getName());
//
//        EmployeeRequestDTO updatedEmployee = new EmployeeRequestDTO();
//        updatedEmployee.setName(requestDTO.getName());
//        updatedEmployee.setDepartment(requestDTO.getDepartment());
//        updatedEmployee.setSalary(requestDTO.getSalary());
//
//        EmployeeResponseDTO emp = service.updateEmployee(id, updatedEmployee);
//
//        if (emp == null) {
//            log.warn("Employee not found with ID: {}", id);
//            throw new ResourceNotFoundException("Employee not found with id: " + id);
//        }
//
//        log.debug("Employee updated successfully: {}", emp.getName());
//        return new EmployeeResponseDTO(emp.getId(), emp.getName(), emp.getDepartment());
//    }
    @PutMapping("/update/{id}")
    public EmployeeResponseDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDTO requestDTO) {
        log.info("PUT /employees/update/{} - Request to update employee: {}", id, requestDTO.getName());

        var updatedEmployeeEntity = service.updateEmployee(id, requestDTO);

        // Map Entity → DTO
        EmployeeResponseDTO empDTO = modelMapper.map(updatedEmployeeEntity, EmployeeResponseDTO.class);

        log.debug("Employee updated successfully: {}", empDTO.getName());
        return empDTO;
    }
    

    // Delete Employee
//    @DeleteMapping("/delete/{id}")
//    public String deleteEmployee(@PathVariable Long id) {
//        log.info("DELETE /employees/delete/{} - Request to delete employee", id);
//
//        service.deleteEmployee(id);
//
//        log.debug("Employee deleted successfully with ID: {}", id);
//        return "Employee deleted successfully!";
//    }
    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        log.info("DELETE /employees/delete/{} - Request to delete employee", id);

        service.deleteEmployee(id);

        log.debug("Employee deleted successfully with ID: {}", id);
        return "Employee deleted successfully!";
    }
}


