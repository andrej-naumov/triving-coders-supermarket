package org.thriving.coders.supermarket.personnel.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "employees_schedules")
public class EmployeeSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduleId")
    private int scheduleId;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @Column(name = "shiftDate")
    private Date shiftDate;

    @Column(name = "shiftType")
    private int shiftType;

    @Column(name = "workStart")
    private Date workStart;

    @Column(name = "workEnd")
    private Date workEnd;

}
