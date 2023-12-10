package org.thriving.coders.supermarket.personnel.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.thriving.coders.supermarket.personnel.entities.Employee;

import java.util.Collections;
import java.util.List;

/**
 * CRUD (create, read, update, delete) with the Employee entity
 */
@Slf4j
public class EmployeeService {

    private final SessionFactory sessionFactory;

    public EmployeeService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * find all employees
     * return in JSON format
     */
    public List<Employee> findAllEmployees() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Employee", Employee.class).list();
        } catch (Exception e) {
            log.error("EmployeeService.findAllEmployees - error: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public Employee findEmployeeById(int employeeId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Employee.class, employeeId);
        } catch (Exception e) {
            log.error("EmployeeService.findEmployeeById - error: " + e.getMessage());
        }
        return null;
    }

    public void saveEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("EmployeeService.saveEmployee - error: " + e.getMessage());
        }
    }

    public void updateEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("EmployeeService.updateEmployee - error: " + e.getMessage());
        }
    }

    public void deleteEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("EmployeeService.deleteEmployee - error: " + e.getMessage());
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
