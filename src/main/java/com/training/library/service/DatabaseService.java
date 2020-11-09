package com.training.library.service;

import com.training.library.entity.Book;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseService {

    private final String url = "jdbc:mysql://localhost:3306/library?serverTimezone=Europe/Moscow&useSSL=false";
    private final String username = "root";
    private final String password = "12345";

    public DatabaseService() {
    }

    public Connection setConnection() throws
            ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException,
            SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        return DriverManager.getConnection(url, username, password);
    }

    public void insert(Book book, Connection conn) throws SQLException {

        String sql = "INSERT INTO books (bookname, author) Values (?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, book.getBookName());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.executeUpdate();

    }

    public List<Book> selectAll(Connection conn) {

        List<Book> books = new ArrayList<Book>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String bookname = resultSet.getString(2);
                String author = resultSet.getString(3);
                Book book = new Book(id, bookname, author);
                books.add(book);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return books;
    }

    public void update(Book book, Connection conn) throws SQLException {
        String sql = "UPDATE books SET bookname = ?, author = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setLong(3, book.getId());
            preparedStatement.executeUpdate();
        }

    }

    public Book selectById(Long id, Connection conn) throws SQLException {
        String sql = "SELECT * FROM books WHERE id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String bookName = resultSet.getString(2);
                String author = resultSet.getString(3);
                return new Book(id, bookName, author);
            } else {
                //
            }
            return null;
        }
    }
    public void delete(Long id, Connection conn) throws SQLException {
                String sql = "DELETE FROM books WHERE id = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setLong(1, id);
                    preparedStatement.executeUpdate();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }

}
}