package com.training.library;
import com.training.library.service.DatabaseService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

public class DeleteBookServlet extends HttpServlet {
    DatabaseService databaseService = new DatabaseService();
    protected void doPost( HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        Long id = Long.parseLong(request.getParameter("id"));
        try(Connection conn = databaseService.setConnection()) {
            databaseService.delete(id, conn);
            response.sendRedirect(request.getContextPath() + "/");
        } catch (Exception ex) {
            printWriter.println("Failed to delete a book.");
        }
    }
}
