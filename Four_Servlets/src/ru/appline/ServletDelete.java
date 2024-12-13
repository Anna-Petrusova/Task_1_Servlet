package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/delete")

public class ServletDelete extends HttpServlet {

    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{

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

        int id = jobj.get("id").getAsInt();

        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();

        if (id==0){
            model.deleteAll();
            pw.print(gson.toJson("Все пользователи удалены"));
        } else if (id>0) {
            if (!model.haveId(id))
            {
                pw.print(gson.toJson("Такого пользователя нет"));
            }
            else{
                model.deleteById(id);
                pw.print(gson.toJson(model.getFrontList()));
            }
        }else {
            pw.print(gson.toJson("ID должен быть больше 0"));
        }

    }
}
