package com.training.library.service;

import com.mysql.cj.jdbc.Driver;
import com.training.library.entity.Book;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseService {

    public DatabaseService() {
    }

    public Connection setConnection() throws
            SQLException, IOException {
        FileInputStream fis;
        Properties property = new Properties();
            fis = new FileInputStream("C:\\Users\\expla\\IdeaProjects\\demo\\src\\main\\resources\\config.properties");
            property.load(fis);
        Driver driver = new Driver();
        DriverManager.registerDriver(driver);
        return  DriverManager.getConnection(
                property.getProperty("db.host"),
                property.getProperty("db.login"),
                property.getProperty("db.password"));
    }

    public void insert(Book book, Connection conn) throws SQLException {

        String sql = "INSERT INTO books (bookname, author) Values (?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, book.getBookName());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.executeUpdate();
        conn.close();
    }

    public List<Book> selectAll(Connection conn) throws SQLException {
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
            ex.printStackTrace();
        } finally {
            conn.close();
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
                return null;
            }

        }
    }
    public void delete(Long id, Connection conn) throws SQLException {
                String sql = "DELETE FROM books WHERE id = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setLong(1, id);
                    preparedStatement.executeUpdate();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

}
}