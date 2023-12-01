package org.thriving.coders.supermarket.personnel.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    Long id;

    @Getter
    @Setter
    @Column(name = "firstName")
    String firstName;

    @Getter
    @Setter
    @Column(name = "lastName")
    String lastName;
}
