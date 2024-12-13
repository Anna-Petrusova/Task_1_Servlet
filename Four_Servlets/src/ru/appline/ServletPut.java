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

@WebServlet(urlPatterns = "/put")

public class ServletPut extends HttpServlet {

    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        StringBuffer buff = new StringBuffer();
        String line;

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

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


        String name = jobj.get("name").getAsString();
        String surname = jobj.get("surname").getAsString();
        double salary = jobj.get("salary").getAsDouble();
        int id = jobj.get("id").getAsInt();

        PrintWriter pw = response.getWriter();

        if (id>0) {
            if (!model.haveId(id))
            {
                pw.print(gson.toJson("Такого пользователя нет"));
            }
            else{
                User user = new User(name, surname, salary);
                model.add(user, id);

                pw.print(gson.toJson(model.getFrontList()));
            }
        }else{
            pw.print(gson.toJson("id должен быть больше 0"));
        }

    }
}
