<%@ page
  language="java"
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
  import="java.util.Date"
%>
<%
  String msg = (String)(request.getAttribute("name") != null ? request.getAttribute("name") : "user");
%>
<html>
  <body>
    <form method="POST" action="hello">
      <input type="text" name="name" value="<%= msg %>"></input>
      <input type="submit" value="送信"></input>
    </form>
    <h2>Hello, <%= msg %>!</h2>
    <%= new Date().toString() %>
  </body>
</html>

