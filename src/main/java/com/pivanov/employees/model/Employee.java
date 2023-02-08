package com.pivanov.employees.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Employee {

    @Id
    private int id;

    private int projectId;

    @NotNull
    private String dateFrom;

    private String dateTo;

    public Employee(int id, int projectId, String dateFrom, String dateTo) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.projectId = projectId;
    }
}
