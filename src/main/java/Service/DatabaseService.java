package Service;

import Entities.Book;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;


public class DatabaseService {

    private static final String url = "jdbc:mysql://localhost:3306/library?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String username = "root";
    private static final String password = "12345";

    public DatabaseService() {
    }

    public static Connection setConnection() throws
            ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException,
            SQLException
    {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            return DriverManager.getConnection(url, username, password);
    }

    public static void insert(Book book, Connection conn) throws SQLException {

                String sql = "INSERT INTO books (bookname, author) Values (?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, book.getBookName());
                    preparedStatement.setString(2, book.getAuthor());

                    preparedStatement.executeUpdate();

    }

    public static ArrayList<Book> selectAll(Connection conn) {

        ArrayList<Book> books = new ArrayList<Book>();
        try{
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM books");
                while(resultSet.next()){
                    Long id = resultSet.getLong(1);
                    String bookname = resultSet.getString(2);
                    String author = resultSet.getString(3);
                    Book book = new Book(id, bookname, author);
                    books.add(book);
                }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return books;
    }
}
