import Entities.Book;
import Service.DatabaseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/add")
public class AddServlet extends HttpServlet {

    DatabaseService databaseService = new DatabaseService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();

        try {
            databaseService = new DatabaseService();
            Connection conn = databaseService.setConnection();
            String bookName = request.getParameter("bookname");
            String author = request.getParameter("author");
            Book book = new Book(bookName, author);
            databaseService.insert(book, conn);
            writer.println("Good operation");
        }
        catch(Exception ex) {
            ex.printStackTrace();
            writer.println(ex);
        }
    }
}
