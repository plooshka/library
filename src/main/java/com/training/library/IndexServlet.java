package com.training.library;

import com.training.library.entity.Book;
import com.training.library.service.DatabaseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class IndexServlet extends HttpServlet {
    DatabaseService databaseService = new DatabaseService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        List<Book> books = new ArrayList<Book>();
        try(Connection conn = databaseService.setConnection()) {
            books = databaseService.selectAll(conn);
        } catch (Exception exception) {
            exception.fillInStackTrace().printStackTrace();
        }
        request.setAttribute("books", books);
        getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
    }
}