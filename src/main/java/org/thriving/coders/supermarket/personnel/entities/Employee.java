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
    Integer id;

    @Column(name = "firstName")
    String firstName;

    @Column(name = "lastName")
    String lastName;

    @Column(name = "position")
    String position;
}
