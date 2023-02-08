package com.pivanov.employees.service;

import com.pivanov.employees.model.Employee;
import com.pivanov.employees.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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

import static java.time.temporal.ChronoUnit.YEARS;


@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> readEmployeesFromCSV(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        Path pathToFile = Paths.get(filePath);

        try (BufferedReader bufferedReader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                Employee employee = createEmployee(attributes);
                employees.add(employee);
                line = bufferedReader.readLine();
            }
        } catch (IOException ioe) {
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
        List<String> formatStrings = Arrays.asList("yyyy-MM-dd", "dd-MM-yyyy", "y/M/d", "d/M/y", "M/y", "M/d/y", "M-d-y", "yyyy.MM.dd", "yyyy/MM/dd");

        for (String formatString : formatStrings) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString, Locale.ENGLISH);
                LocalDate dateTime = LocalDate.parse(dateString, formatter);
                return dateTime;
            } catch (DateTimeParseException e) {
                System.out.println("exception");
            }
        }
        return null;
    }

    // Implementation of this method can be significantly improved with streams. Here is just a raw version that works.
    public void calculateWorkTime(String filePath, HttpSession session) throws IOException {
        List<Employee> employeeList = readEmployeesFromCSV(filePath);
        List<Long> pairWorkDaysList = new ArrayList<>();
        int emp1Id;
        int emp2Id;
        int j;
        int i = 0;
        while (i < employeeList.size() - 1) {
            j = i + 1;
            while (j <= employeeList.size() - 1) {
                if (employeeList.get(i).getProjectId() == employeeList.get(j).getProjectId()) {
                    LocalDate comparisonDateFrom = employeeList.get(i).getDateFrom();
                    LocalDate comparisonDateTo = employeeList.get(i).getDateTo();

                    if (employeeList.get(i).getDateFrom().isBefore(employeeList.get(j).getDateFrom())) {
                        comparisonDateFrom = employeeList.get(j).getDateFrom();
                    }
                    if (employeeList.get(i).getDateTo().isAfter(employeeList.get(j).getDateTo())) {
                        comparisonDateTo = employeeList.get(j).getDateTo();
                    }
                    long daysBetween = YEARS.between(comparisonDateFrom, comparisonDateTo);
                    pairWorkDaysList.add(daysBetween);
                    long longestPeriod = Collections.max(pairWorkDaysList);
                    session.setAttribute("longestPeriod", longestPeriod);
                    if (daysBetween == longestPeriod) {
                        emp1Id = employeeList.get(i).getId();
                        emp2Id = employeeList.get(j).getId();
                        session.setAttribute("emp1Id", emp1Id);
                        session.setAttribute("emp2Id", emp2Id);
                    }
                }
                j++;
            }
            i++;
        }
    }
}
