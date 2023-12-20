package org.thriving.coders.supermarket.personnel.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees_permissions")
public class EmployeePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accessId")
    private int accessId;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @Column(name = "login")
    private String login;

    @Column(name = "employeeInternalNumber")
    private int employeeInternalNumber;

    @Column(name = "passwordHash")
    private String passwordHash;

    @Column(name = "accessRights", columnDefinition = "LONGTEXT")
    private String accessRights;
}

