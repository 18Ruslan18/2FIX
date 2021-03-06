package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

//@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    private Connection connection;
    @Override
    public void init() throws ServletException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String driverClassName = properties.getProperty("db.driverClassName");

            Class.forName(driverClassName);
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (IOException  e) {
            throw new IllegalStateException(e);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/addUser.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstname= req.getParameter("first-name");
        String lastname = req.getParameter("last-name");
        try {
            ///Statement statement = connection.createStatement();
           /// String sqlInsert = "INSERT INTO fix_user (firstName, lastName) " +
                   /// "VALUES('" + firstname + "','" + lastname + "');";
           /// statement.execute(sqlInsert);
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO " +
                            "fix_user(firstName, lastName) VALUES (?, ?)");
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        doGet(req,resp);
    }
}
