<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CRUD</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-light" style="background-color: bisque">
        <div>
            <a href="<%=request.getContextPath()%>" class="navbar-brand"> CRUD </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/list" class="nav-link">Users</a></li>
        </ul>
    </nav>
</header>
<br/>

<div class="row">
    <div class="container">
        <h3 class="text-center">USERS</h3>
        <hr>
        <div class="container text-center">
            <a href="<%=request.getContextPath()%>/new" class="btn btn-success">ADD NEW USER</a>
            <a href="<%=request.getContextPath()%>/delete-all" class="btn btn-success">DELETE ALL</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${listUser}">
                <tr>
                    <td>
                        <c:out value="${user.id}"/>
                    </td>
                    <td>
                        <c:out value="${user.name}"/>
                    </td>
                    <td>
                        <c:out value="${user.email}"/>
                    </td>
                    <td>
                        <a href="edit?id=<c:out value='${user.id}' />">EDIT</a>
                        &nbsp;&nbsp;&nbsp;
                        <a href="delete?id=<c:out value='${user.id}' />">DELETE</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>