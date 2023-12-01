package org.thriving.coders.supermarket.personnel.controller;

import static spark.Spark.*;
import org.thriving.coders.supermarket.personnel.services.EmployeeService;

public class EmployeeController {

    public EmployeeController(final EmployeeService employeeService) {

        get("/employees", (req, res) -> employeeService.getAllEmployees() );
    }
}
