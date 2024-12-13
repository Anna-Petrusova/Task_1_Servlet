<%@page import="ru.appline.logic.Model"%>
<%--
  Created by IntelliJ IDEA.
  User: Рабочий
  Date: 10.12.2024
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
  <h1> Homepage work with users </h1>
  Введите Id пользователя (0 - для вывода списка)
  <br/>
  Доступно: <%
  Model model = Model.getInstance();
  out.print(model.getFrontList().size());
  %>
  <form method="get" action="get">
    <label>Id
      <input type="text" name="id">
    </label>
    <button type="submit">Search</button>
  </form>
  <a href="addUser.html">Добавить нового пользователя</a></br>
  <a href="deleteUser.html"> На страницу удаления </a></br>
  <a href="putUser.html"> Обновить данные пользователя </a></br>
  </body>
</html>
