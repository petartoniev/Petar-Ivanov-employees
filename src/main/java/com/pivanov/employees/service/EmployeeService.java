package com.pivanov.employees.service;

import com.pivanov.employees.model.Employee;
import com.pivanov.employees.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    public List<Employee> readEmployeesFromCSV(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        Path pathToFile = Paths.get(filePath);

        try(BufferedReader bufferedReader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = bufferedReader.readLine();
            while(line != null) {
                String[] attributes = line.split(",");
                Employee employee = createEmployee(attributes);

                employees.add(employee);
                line = bufferedReader.readLine();
            }
        }   catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return employees;
    }

    private static Employee createEmployee(String[] metadata) {
        int empId = Integer.parseInt(metadata[0]);
        int projectId = Integer.parseInt(metadata[1]);
        String dateFrom = metadata[2];
        String dateTo = metadata[3];
        return new Employee(empId, projectId, dateFrom, dateTo);
    }

}
