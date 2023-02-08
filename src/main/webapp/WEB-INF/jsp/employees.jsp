<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1> Employee data successfully uploaded.</h1>
<li><a href="/home" >Navigate to home page</a></li>
<div>
<table>
<thead>
          <tr>
            <td>
              <h3> EmpId | </h3>
            </td>
            <td>
              <h3> ProjectId | </h3>
            </td>
            <td>
              <h3> DateFrom | </h3>
            </td>
            <td>
              <h3> DateTo </h3>
            </td>
          </tr>
        </thead>
        <tbody>
<c:choose>
<c:when test="${employeeList!=null}">
<c:forEach var="entry" items="${employeeList}">
<tr>
<td> <c:out value="${entry.id}"/> </td>
<td> <c:out value="${entry.projectId}"/> </td>
<td> <c:out value="${entry.dateFrom}"/> </td>
<td> <c:out value="${entry.dateTo}"/> </td>
</tr>
<br>
</c:forEach>
</c:when>
<c:otherwise>
<c:out value="There are no registered employees.">
</c:out>
</c:otherwise>
</c:choose>
</tbody>
</table>
</div>
