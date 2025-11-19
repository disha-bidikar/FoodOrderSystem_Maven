<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Menu</title>
</head>
<body>
  <h1>Menu</h1>
  <table border="1">
    <tr><th>ID</th><th>Name</th><th>Description</th><th>Price</th></tr>
    <c:forEach var="f" items="${foods}">
      <tr>
        <td>${f.foodId}</td>
        <td>${f.name}</td>
        <td>${f.description}</td>
        <td>${f.price}</td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>
