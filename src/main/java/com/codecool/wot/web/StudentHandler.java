package com.codecool.wot.web;

import com.codecool.wot.dao.*;
import com.codecool.wot.model.Admin;
import com.codecool.wot.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class StudentHandler implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");

        if (cookieStr != null) {
            URI uri = httpExchange.getRequestURI();
            CookieDAO cookieDAO = new CookieDAO(DatabaseConnection.getDBConnection().getConnection());
            Integer userId = cookieDAO.getUserIdBySessionId(cookieStr);
            StudentDAO studentDAO = new StudentDAO(DatabaseConnection.getDBConnection().getConnection());
            Student student = studentDAO.getById(userId);

            if (student != null && Integer.toString(userId).equals(parseURIToGetId(uri.getPath()))) {
                ClassDAO classDAO = new ClassDAO(DatabaseConnection.getDBConnection().getConnection());
                JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student.html");
                JtwigModel model = JtwigModel.newModel();

                model.with("name", student.getName());
                String response = template.render(model);

                httpExchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                handleWrongUser(httpExchange);
            }
        } else {
            handleWrongUser(httpExchange);
        }
    }


    private void handleWrongUser(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().set("Location", "/");
        httpExchange.sendResponseHeaders(302,-1);
    }

    private String parseURIToGetId(String uri) {
        String userIdFromURI = "";
        String[] pairs = uri.split("/");
        userIdFromURI = pairs[2];

        return userIdFromURI;
    }




}