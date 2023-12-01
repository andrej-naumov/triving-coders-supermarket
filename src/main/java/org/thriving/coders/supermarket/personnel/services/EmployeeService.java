package org.thriving.coders.supermarket.personnel.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.thriving.coders.supermarket.personnel.entities.Employee;

import java.util.List;

/**
 * CRUD (create, read, update, delete) with the Employee entity
 */
@Slf4j
public class EmployeeService {

    private final SessionFactory sessionFactory;
    private final ObjectMapper objectMapper; // ObjectMapper from Jackson


    public EmployeeService(SessionFactory sessionFactory, ObjectMapper objectMapper) {
        this.sessionFactory = sessionFactory;
        this.objectMapper = objectMapper;
    }

    /**
     * find all employees
     * return in JSON format
     */
    public String findAllEmployeesAsJson() {
        try (Session session = sessionFactory.openSession()) {
            List<Employee> employees = session.createQuery("from Employee", Employee.class).list();
            return objectMapper.writeValueAsString(employees); //  JSON - String as return
        } catch (Exception e) {
            // TODO
            log.error("findAllEmployees - error: " + e.getMessage());
            return null;
        }
    }

    public Employee findEmployeeById(int employeeId) {
        try (Session session = sessionFactory.openSession()) {
            Employee employee = session.get(Employee.class, employeeId);
            if (employee != null) {
                return employee;
            }
        } catch (Exception e) {
            // Обработка исключений или логирование ошибок
            e.getStackTrace();
        }
        return null;
    }

    public String findEmployeeByIdAsJson(int employeeId) {
        try {
            return objectMapper.writeValueAsString(this.findEmployeeById(employeeId));
        } catch (JsonProcessingException e) {
            // Обработка исключений или логирование ошибок
            //throw new RuntimeException(e);
        }

        return null;
    }

    public void saveEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            // Обработка исключений или логирование ошибок
        }
    }

    public void updateEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            // Обработка исключений или логирование ошибок
        }
    }

    public void deleteEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            // Обработка исключений или логирование ошибок
        }
    }

    /*
    *     public List<Employee> findEmployeesByDepartmentAsJson(int departmentId) {
        try (Session session = sessionFactory.openSession()) {
            List<Employee> employees = session.createQuery("from Employee where department_id = :deptId", Employee.class)
                    .setParameter("deptId", departmentId)
                    .list();
            return employees;
        } catch (Exception e) {
            // Обработка исключений или логирование ошибок
            return null;
        }
    }
    * */
}
