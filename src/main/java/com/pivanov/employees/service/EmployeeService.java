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
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


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
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return employees;
    }

    private static Employee createEmployee(String[] metadata) throws ParseException {
        int empId = Integer.parseInt(metadata[0]);
        int projectId = Integer.parseInt(metadata[1]);
        LocalDate dateFrom = tryParse(metadata[2].trim());
        LocalDate dateTo = tryParse(metadata[3].trim());
        return new Employee(empId, projectId, dateFrom, dateTo);
    }

    private static LocalDate tryParse(String dateString) {
        List<String> formatStrings = Arrays.asList("yyyy-MM-dd","dd-MM-yyyy","y/M/d","d/M/y", "M/y", "M/d/y", "M-d-y", "yyyy.MM.dd", "yyyy/MM/dd");

        for (String formatString : formatStrings)
        {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString, Locale.ENGLISH);
                LocalDate dateTime = LocalDate.parse(dateString, formatter);
                return dateTime;
            } catch (DateTimeParseException e)
            {
                System.out.println("exception");
            }
        }
        return null;
    }

}
