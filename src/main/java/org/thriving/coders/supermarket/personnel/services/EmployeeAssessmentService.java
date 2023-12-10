package org.thriving.coders.supermarket.personnel.services;

import org.hibernate.SessionFactory;

public class EmployeeAssessmentService {

    private final SessionFactory sessionFactory;

    public EmployeeAssessmentService(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

}
