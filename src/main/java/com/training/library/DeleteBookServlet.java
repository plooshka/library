package com.training.library;

import com.training.library.service.DatabaseService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class DeleteBookServlet extends HttpServlet {
    DatabaseService databaseService = new DatabaseService();
    protected void doPost( HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.parseLong(request.getParameter("id"));
        try(Connection conn = databaseService.setConnection()) {
            databaseService.delete(id, conn);
            response.sendRedirect(request.getContextPath() + "/");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
