package org.thriving.coders.supermarket.personnel.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "employees_assessments")
public class EmployeeAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessmentId")
    private int assessmentId;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @Column(name = "assessmentDate")
    private Date assessmentDate;

    @Column(name = "performanceRating")
    private int performanceRating;

    @Column(name = "salesAnalysis")
    private int salesAnalysis;

}
