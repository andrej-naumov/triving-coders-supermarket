package org.thriving.coders;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.thriving.coders.supermarket.personnel.controller.EmployeeController;
import org.thriving.coders.supermarket.personnel.services.EmployeeService;
import org.thriving.coders.supermarket.util.HibernateUtil;
import spark.Request;
import spark.Response;

import static spark.route.HttpMethod.options;
import static spark.Spark.*;

@Slf4j
public class Main {
    public static void main(String[] args) {
        // server startup information
        log.info("server goes live...");

        // enable CORS for all req from http://localhost:4200
        options("/*", (request, response) -> {
            addEnableCORS_Headers(request, response);

            return "OK";
        });

        // enable CORS for all req from http://localhost:4200
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "http://localhost:4200");
            response.header("Access-Control-Allow-Credentials", "true");
        });
        //~

        final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        final ObjectMapper objectMapper = new ObjectMapper();

        // register controllers and services
        new EmployeeController(new EmployeeService(sessionFactory), objectMapper);

        // server startup information
        log.info("...server is running");
    }

    private static void addEnableCORS_Headers(Request request, Response response) {
        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
        if (accessControlRequestHeaders != null) {
            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
        }

        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
        if (accessControlRequestMethod != null) {
            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
        }
    }
}