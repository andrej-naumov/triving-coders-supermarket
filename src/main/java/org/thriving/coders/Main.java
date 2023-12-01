package org.thriving.coders;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.thriving.coders.supermarket.personnel.controller.EmployeeController;
import org.thriving.coders.supermarket.personnel.services.EmployeeService;
import org.thriving.coders.supermarket.util.HibernateUtil;

import static spark.Spark.get;

@Slf4j
public class Main {
    public static void main(String[] args) {

        // server startup information
        log.info("server goes live...");

        final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        final ObjectMapper objectMapper = new ObjectMapper();

        // register controllers and services
        new EmployeeController(new EmployeeService(sessionFactory, objectMapper), objectMapper);

        get("/hello", (req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Hello world\"}";
        });


        // server startup information
        log.info("...server is running");
    }

}