package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import javax.jws.soap.SOAPBinding;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet {

    private AtomicInteger counter = new AtomicInteger(5);
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        if ("application/json".equals(request.getContentType())) {
        StringBuffer buff = new StringBuffer();
        String line;

        ///чтение данных из тела запроса
        try{
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
            {
                buff.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error!");
        }

        JsonObject jobj = gson.fromJson(String.valueOf(buff), JsonObject.class);
        request.setCharacterEncoding("UTF-8");

        String name = jobj.get("name").getAsString();
        String surname = jobj.get("surname").getAsString();
        double salary = jobj.get("salary").getAsDouble();

        User user = new User(name, surname, salary);
        model.add(user, counter.getAndIncrement());

        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(gson.toJson(model.getFrontList()));
      } else {
            response.setContentType("text/html; charset=utf-8");
            request.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();

            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            double salary = Double.parseDouble(request.getParameter("salary"));

            User user = new User(name, surname, salary);
            model.add(user, counter.getAndIncrement());

            pw.print("<html>"+
                    "<h3>Пользователь " + name + ' ' + surname + " с зарплатой " + salary +
                    " успешно создан </h3>" +
                    "<a href=\"addUser.html\">Add new user</a></br>" +
                    "<a href=\"index.jsp\"> Home </a>" +
                    "</html>"
            );
        }


    }
}
