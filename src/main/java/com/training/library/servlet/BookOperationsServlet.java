package com.training.library.servlet;

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

public class BookOperationsServlet extends HttpServlet {
    DatabaseService databaseService = new DatabaseService();
    PrintWriter writer = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String currentPath = request.getContextPath();
        //index
        //if (currentPath == "/demo_war") {
            PrintWriter printWriter = response.getWriter();
            List<Book> books = new ArrayList<Book>();
            try (Connection conn = databaseService.setConnection()) {
                books = databaseService.selectAll(conn);
            } catch (Exception exception) {
                printWriter.println("Failed to display the content");
            }
            request.setAttribute("books", books);
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
       // }
        //add
       // if((currentPath + "add") == "/demo_war/add") {
            getServletContext().getRequestDispatcher("/create.jsp").forward(request, response);
        //}
        //edit
       // if((currentPath + "/edit") == "/demo_war/edit" ) {
            writer = response.getWriter();
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                Book book = databaseService.selectById(id, databaseService.setConnection());
                if (book != null) {
                    request.setAttribute("book", book);
                    getServletContext().getRequestDispatcher("/edit.jsp").forward(request, response);
                } else {
                    getServletContext().getRequestDispatcher("/index.jsp").forward(null, null);
                }
            } catch (Exception ex) {
                writer.println("Failed to get data");
            }
       // }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        writer = response.getWriter();
        //add
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
        //delete
        writer = response.getWriter();
        Long id = Long.parseLong(request.getParameter("id"));
        try(Connection conn = databaseService.setConnection()) {
            databaseService.delete(id, conn);
            response.sendRedirect(request.getContextPath() + "/");
        } catch (Exception ex) {
            writer.println("Failed to delete a book.");
        }
        //edit
        try {
            Long id1 = Long.parseLong(request.getParameter("id"));
            String bookName = request.getParameter("bookname");
            String author = request.getParameter("author");
            Book book = new Book(id1, bookName, author);
            databaseService.update(book, databaseService.setConnection());
            response.sendRedirect(request.getContextPath() + "/");
        }
        catch(Exception ex) {
            getServletContext().getRequestDispatcher("/notfound.html").forward(request, response);
        }

    }

}
