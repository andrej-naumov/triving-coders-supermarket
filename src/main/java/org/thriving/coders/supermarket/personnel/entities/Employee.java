package org.thriving.coders.supermarket.personnel.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID generation strategy
    @Column(name = "employeeId")
    Integer employeeId; // Employee's primary key

    @Column(name = "firstName")
    String firstName; // Employee's first name field

    @Column(name = "lastName")
    String lastName; // Employee's last name field

    @Column(name = "position")
    String position; // Employee's position field

    @Column(name = "department")
    private int department;

    @Column(name = "contactInfo")
    private String contactInfo;

    @Column(name = "hourlyRate")
    private int hourlyRate;

    @Column(name = "commentary")
    private String commentary;
}
