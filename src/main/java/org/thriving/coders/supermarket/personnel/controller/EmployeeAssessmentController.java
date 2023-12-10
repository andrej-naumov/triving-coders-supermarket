package org.thriving.coders.supermarket.personnel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.thriving.coders.supermarket.personnel.entities.EmployeeAssessment;
import org.thriving.coders.supermarket.personnel.services.EmployeeAssessmentService;
import org.thriving.coders.supermarket.personnel.services.EmployeeService;
import org.thriving.coders.supermarket.util.JsonTransformer;
import org.thriving.coders.supermarket.util.Message;
import org.thriving.coders.supermarket.util.MessageLevel;

import java.util.List;

import static spark.Spark.*;
@Slf4j
public class EmployeeAssessmentController {
    private final EmployeeAssessmentService employeeAssessmentService;
    private final ObjectMapper objectMapper;

    public EmployeeAssessmentController(final EmployeeAssessmentService employeeAssessmentService, ObjectMapper objectMapper) {
        this.employeeAssessmentService = employeeAssessmentService;
        this.objectMapper = objectMapper;
        setupRoutes();
    }

    private void setupRoutes() {
        // find all assessments for employee
        get("employees/:id/assessments", (req, res) -> {
            res.type("application/json");
            int employeeId = Integer.parseInt(req.params(":id"));
            List<EmployeeAssessment> assessments = employeeAssessmentService.findAllAssessmentByEmployeesId(employeeId);
            if(assessments.size() != 0) {
                log.info("Find all assessments for employee with id: {} -- count of assessments is: {}", employeeId, assessments.size());
                return assessments;
            } else {
                log.warn("TODO");
                return new Message(MessageLevel.INFO, "No assessments found!" );
            }
        }, new JsonTransformer());
    }

}
