package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

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

@WebServlet(urlPatterns = "/calculator")

public class ServletCalculator extends HttpServlet {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
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

        double a = jobj.get("a").getAsDouble();
        double b = jobj.get("b").getAsDouble();
        String math = jobj.get("math").getAsString();


        PrintWriter pw = response.getWriter();

        String c = Character.toString('-');
        if (math.equals("-")) {
            pw.print("result:"+gson.toJson(a - b));
        }
        else if (math.equals("+")) {
            pw.print("result:"+gson.toJson(a + b));
        }
        else if (math.equals("*")) {
            pw.print("result:"+gson.toJson(a * b));
        }
        else if (math.equals("/")) {
            if (b==0){
                pw.print(gson.toJson("Деление на 0 не допускается!"));
            }
            else
            {
                pw.print("result:"+gson.toJson(a / b));
            }
        }
        else {
            pw.print(gson.toJson("Допустимы только знаки +, -, *, /"));
        }


    }
}
