package org.thriving.coders.supermarket.personnel.controller;

import static spark.Spark.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.thriving.coders.supermarket.personnel.entities.Employee;
import org.thriving.coders.supermarket.personnel.services.EmployeeService;

public class EmployeeController {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;
    public EmployeeController(final EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
        setupRoutes();
    }

    private void setupRoutes() {
        // find all employees
        get("/employees", (req, res) ->
        {
            res.type("application/json");
            return employeeService.findAllEmployeesAsJson() ;
        });

        // find employee with id
        get("/employees/:id", (req, res) -> {
            res.type("application/json");
            int employeeId = Integer.parseInt(req.params("id"));
            return employeeService.findEmployeeByIdAsJson(employeeId);
        });

        // save new employee
        post("/employees", (req, res) -> {
            res.type("application/json");
            Employee employee = objectMapper.readValue(req.body(), Employee.class);
            employeeService.saveEmployee(employee);
            return "Employee saved successfully!";
        });

        // update employee
        put("/employees/:id", (req, res) -> {
            res.type("application/json");
            int employeeId = Integer.parseInt(req.params("id"));
            Employee updatedEmployee = objectMapper.readValue(req.body(), Employee.class);
            updatedEmployee.setId(employeeId);
            employeeService.updateEmployee(updatedEmployee);
            return "Employee updated successfully!";
        });

        // delete employee
        delete("/employees/:id", (req, res) -> {
            res.type("application/json");
            int employeeId = Integer.parseInt(req.params("id"));
            Employee employeeToDelete = employeeService.findEmployeeById(employeeId);
            if (employeeToDelete != null) {
                employeeService.deleteEmployee(employeeToDelete);
                return "Employee deleted successfully!";
            } else {
                res.status(404);
                return "Employee not found!";
            }
        });
    }
}
