package com.pivanov.employees.controller;

import com.pivanov.employees.model.Employee;
import com.pivanov.employees.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping
@RequiredArgsConstructor
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 *1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
public class EmployeeController {

    private final EmployeeService employeeService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getHomePage(HttpServletRequest request) {
        return "index";
    }

    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public String listAllEmployees(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        String filePath = "C:/Users/p.ivanov/Desktop/uploadedFiles/" + fileName;
        for(Part part : request.getParts()) {
            part.write(filePath);
        }
        List<Employee> employeeList = employeeService.readEmployeesFromCSV(filePath);
        request.setAttribute("employeeList", employeeList);

        return "employees";
    }

}
