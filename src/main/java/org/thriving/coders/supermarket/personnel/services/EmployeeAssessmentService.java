package org.thriving.coders.supermarket.personnel.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.thriving.coders.supermarket.personnel.entities.EmployeeAssessment;

import java.util.Collections;
import java.util.List;
@Slf4j
public class EmployeeAssessmentService {

    private final SessionFactory sessionFactory;

    public EmployeeAssessmentService(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public List<EmployeeAssessment> findAllAssessmentByEmployeesId(int employeeId) {
        try (Session session = sessionFactory.openSession()) {
            Query<EmployeeAssessment> query = session.createQuery(
                    "FROM EmployeeAssessment WHERE employee.employeeId = :employeeId",
                    EmployeeAssessment.class
            );
            query.setParameter("employeeId", employeeId);
            return query.list();
        } catch (Exception e) {
            log.error("EmployeeAssessmentService.findAllAssessmentByEmployeesId - error: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
