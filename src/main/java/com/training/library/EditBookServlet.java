package com.training.library;

import com.training.library.entity.Book;
import com.training.library.service.DatabaseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EditBookServlet extends HttpServlet {
    DatabaseService databaseService = new DatabaseService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter writer = response.getWriter();
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            writer.println("Good operation");
            Book book = databaseService.selectById(id, databaseService.setConnection());
            writer.println("Good operation");
            if(book!=null) {
                request.setAttribute("book", book);
                writer.println("Good operation");
                getServletContext().getRequestDispatcher("/edit.jsp").forward(request, response);
            }
            else {
                getServletContext().getRequestDispatcher("/index.jsp").forward(null, null);
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            String bookName = request.getParameter("bookname");
            String author = request.getParameter("author");
            Book book = new Book(id, bookName, author);
            databaseService.update(book, databaseService.setConnection());
            response.sendRedirect(request.getContextPath() + "/");
        }
        catch(Exception ex) {
            getServletContext().getRequestDispatcher("/notfound.html").forward(request, response);
        }
    }
}

