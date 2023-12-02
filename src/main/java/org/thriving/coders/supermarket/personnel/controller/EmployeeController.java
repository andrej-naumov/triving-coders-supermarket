package org.thriving.coders.supermarket.personnel.controller;

import static spark.Spark.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.thriving.coders.supermarket.personnel.entities.Employee;
import org.thriving.coders.supermarket.personnel.services.EmployeeService;
import org.thriving.coders.supermarket.util.JsonTransformer;
import org.thriving.coders.supermarket.util.Message;
import org.thriving.coders.supermarket.util.MessageLevel;

import java.util.List;
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;
    /**
     * Constructs the EmployeeController.
     * @param employeeService The service handling employee-related operations.
     * @param objectMapper The object mapper for JSON serialization/deserialization.
     */
    public EmployeeController(final EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
        setupRoutes();
    }

    /**
     * Sets up the RESTful routes for employee operations.
     */
    private void setupRoutes() {

        // Find all employees
        get("/employees", (req, res) -> {
            res.type("application/json");
            List<Employee> employees = employeeService.findAllEmployees();
            if (employees != null) {
                log.info("Find all employees. Count of employees: {}", employees.size());
                return employees;
            } else {
                log.warn("Find all employees, but no employees found!");
                return new Message(MessageLevel.INFO, "Employees not found!");
            }
        }, new JsonTransformer());

        // Find employee with id
        get("/employees/:id", (req, res) -> {
            res.type("application/json");
            int employeeId = Integer.parseInt(req.params("id"));
            Employee employee = employeeService.findEmployeeById(employeeId);
            if (employee != null) {
                log.info("Find Employee with id: {} -  is found", employee.getId());
                return employee;
            } else {
                log.info("Employee with id: " + employeeId + " not found!");
                return new Message(MessageLevel.INFO, "Employee not found!");
            }
        }, new JsonTransformer());

        // Save new employee
        post("/employees", (req, res) -> {
            res.type("application/json");
            try {
                Employee employee = objectMapper.readValue(req.body(), Employee.class);
                employeeService.saveEmployee(employee);
                log.info("Employee saved successfully!");
                return new Message(MessageLevel.SUCCESS, "Employee saved successfully!");
            } catch (JsonProcessingException e) {
                log.error("Employee not saved. Error message: " + e.getMessage());
                return new Message(MessageLevel.ERROR, "Employee not saved!");
            }
        }, new JsonTransformer());

        // Update employee
        put("/employees/:id", (req, res) -> {
            res.type("application/json");
            int employeeId = Integer.parseInt(req.params("id"));
            try {
                Employee updatedEmployee = objectMapper.readValue(req.body(), Employee.class);
                updatedEmployee.setId(employeeId);
                employeeService.updateEmployee(updatedEmployee);
                log.info("Employee with id: {} updated successfully!", employeeId);
                return new Message(MessageLevel.SUCCESS, "Employee saved successfully!");
            } catch (JsonProcessingException e) {
                log.error("Employee not updated. Error message: " + e.getMessage());
                return new Message(MessageLevel.ERROR, "Employee not updated!");
            }
        }, new JsonTransformer());

        // Delete employee
        delete("/employees/:id", (req, res) -> {
            res.type("application/json");
            int employeeId = Integer.parseInt(req.params("id"));
            Employee employeeToDelete = employeeService.findEmployeeById(employeeId);
            if (employeeToDelete != null) {
                employeeService.deleteEmployee(employeeToDelete);
                log.info("Employee with id: {} deleted successfully!", employeeId);
                return new Message(MessageLevel.SUCCESS, "Employee deleted successfully!");
            } else {
                log.error("Employee with id {} not found!", employeeId);
                return new Message(MessageLevel.ERROR, "Employee saved successfully!");
            }
        }, new JsonTransformer());
    }
}
