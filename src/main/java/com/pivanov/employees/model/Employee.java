package com.pivanov.employees.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    private int id;

    private int projectId;

    @NotNull
    private LocalDate dateFrom;

    private LocalDate dateTo;

    public Employee(int id, int projectId, LocalDate dateFrom, LocalDate dateTo) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.projectId = projectId;
    }
}
