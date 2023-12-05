package org.thriving.coders.supermarket.personnel.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "employees_vacations")
public class EmployeeVacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacationId")
    private int vacationId;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "reason")
    private String reason;
}
