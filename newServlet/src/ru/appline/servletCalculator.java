package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/calc")
public class servletCalculator extends HttpServlet {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        StringBuffer jb = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }

        JsonObject jobj = gson.fromJson(String.valueOf(jb), JsonObject.class);

        PrintWriter pw = response.getWriter();

        double a = jobj.get("a").getAsDouble();
        double b = jobj.get("b").getAsDouble();
        String c = jobj.get("math").getAsString();

        Math math = new Math(a,b,c);

        pw.print(gson.toJson(math));

    }
}

class Math {
    double result;
    Math(double a, double b, String c) {
        switch (c) {
            case "*":
                this.result = a * b;
                break;
            case "/":
                this.result = a / b;
                break;
            case "+":
                this.result = a + b;
                break;
            case "-":
                this.result = a - b;
                break;
            default:
                this.result = 0;
                break;
        }
    }
}
