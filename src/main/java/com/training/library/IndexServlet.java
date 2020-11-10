package com.training.library;

import com.training.library.entity.Book;
import com.training.library.service.DatabaseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class IndexServlet extends HttpServlet {
    DatabaseService databaseService = new DatabaseService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        PrintWriter printWriter = response.getWriter();
        List<Book> books = new ArrayList<Book>();
        try(Connection conn = databaseService.setConnection()) {
            books = databaseService.selectAll(conn);
        } catch (Exception exception) {
            printWriter.println("Failed to display the content");
        }
        request.setAttribute("books", books);
        printWriter.println(request.getContextPath());
        //getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
    }
}