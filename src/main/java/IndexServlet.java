import Entities.Book;
import Service.DatabaseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet("")
public class IndexServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Book> books = new ArrayList<Book>();
        try(Connection conn = DatabaseService.setConnection()) {
            books = DatabaseService.selectAll(conn);
        } catch (Exception exception) {
            exception.fillInStackTrace().printStackTrace();
        }
        request.setAttribute("books", books);
        getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
    }
}