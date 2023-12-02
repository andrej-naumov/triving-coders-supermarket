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
    Integer id;

    @Column(name = "firstName")
    String firstName; // Employee's first name field

    @Column(name = "lastName")
    String lastName; // Employee's last name field

    @Column(name = "position")
    String position; // Employee's position field
}
