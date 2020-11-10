package com.training.library.servlet;

import com.sun.tools.sjavac.server.RequestHandler;
import com.training.library.entity.Book;
import com.training.library.service.DatabaseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

public class BookOperationsServlet extends HttpServlet {
    DatabaseService databaseService = new DatabaseService();
    PrintWriter writer = null;
    //ADDBOOKSERVLET
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        writer = response.getWriter();
        try(Connection conn = databaseService.setConnection()) {
            String bookName = request.getParameter("bookname");
            String author = request.getParameter("author");
            Book book = new Book(bookName, author);
            databaseService.insert(book, conn);
            response.sendRedirect(request.getContextPath() + "/");
        }
        catch(Exception ex) {
            writer.println("Failed to add a book. Try again");
        }
    }

    //DELETEBOOKSERVLET
    /*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        Long id = Long.parseLong(request.getParameter("id"));
        try(Connection conn = databaseService.setConnection()) {
            databaseService.delete(id, conn);
            response.sendRedirect(request.getContextPath() + "/");
        } catch (Exception ex) {
            printWriter.println("Failed to delete a book.");
        }
    }*/
}
