package org.thriving.coders;

import static spark.Spark.*;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.impl.StaticLoggerBinder;
import org.thriving.coders.supermarket.personnel.controller.EmployeeController;
import org.thriving.coders.supermarket.personnel.entities.Employee;
import org.thriving.coders.supermarket.personnel.services.EmployeeService;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {

        // test hibernate
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            List<Employee> result = session.createQuery("from Employee", Employee.class).list();

            result.forEach(employee -> {
                System.out.println("person - " + employee.getFirstName());
            });

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            log.error(e.getMessage());
            e.getStackTrace();
        }

        new EmployeeController(new EmployeeService());

        get("/hello", (req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Hello world\"}";
        });
    }
}