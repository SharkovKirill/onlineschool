package nsu_group.ui;

import nsu_group.ui.mvc.UserController;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;
import java.util.Formatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class InSQLUserRepository implements UserRepository{
    private final static String url = "jdbc:mysql://localhost/onlineschool";
//    private final static String url = "jdbc:mysql://127.0.0.1:3306/onlineschool";
    private final static String bduser = "root";
    private final static String bdpassword = "root";

    private final ArrayList<User> users = new ArrayList<User>();

    @Override
    public ArrayList<User> findAll() {
        return null;
    }

    @Override
    public User save(User user) {
        try {
            Connection connection = DriverManager.getConnection(url, bduser, bdpassword);
            Statement statement = connection.createStatement();
            Formatter sq = new Formatter();
            sq.format("INSERT INTO `onlineschool`.`users` (`email`, `name`, `password`, `teacher`, `userlessons`) VALUES ('%s', '%s', '%s', '%s', '%s');",
                    new String(user.getEmail().getBytes("ISO-8859-1"), "UTF-8"), new String(user.getName().getBytes("ISO-8859-1"), "UTF-8"), new String(user.getPassword().getBytes("ISO-8859-1"), "UTF-8"), new String(user.getTeacher().getBytes("ISO-8859-1"), "UTF-8"), new String(user.getUserlessons().getBytes("ISO-8859-1"), "UTF-8"));
            statement.executeUpdate(String.valueOf(sq));
            connection.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return user;
        }
    }

    @Override
    public Boolean check(User user) {
        try {
            Connection connection = DriverManager.getConnection(url, bduser, bdpassword);
            Statement statement = connection.createStatement();
            Statement statementSecond = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users;");
            while (resultSet.next()) {
                String mail = resultSet.getString("email");
                String pas = resultSet.getString("password");
                if(user.getEmail().equals(mail) & user.getPassword().equals(pas)){
                    ResultSet r = statementSecond.executeQuery("SELECT * FROM Users WHERE email='"+mail+ "';");
                    while (r.next()){
                        User us = new User(r.getInt("id"), r.getString("email"), r.getString("name"), r.getString("password"),r.getString("teacher"), r.getString("userlessons"));
                        UserController.user = us;
                        return true;
                    }
                }
            }
            connection.close();
            statement.close();
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    @Override
    public User findUser(User user) {
        try {
            Connection connection = DriverManager.getConnection(url, bduser, bdpassword);
            Statement statement = connection.createStatement();
            Statement statementSecond = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users;");
            while (resultSet.next()) {
                String mail = resultSet.getString("email");
                String pas = resultSet.getString("password");
                if(user.getEmail().equals(mail) & user.getPassword().equals(pas)){
                    ResultSet r = statementSecond.executeQuery("SELECT * FROM Users WHERE email='"+mail+ "';");
                    while (r.next()){
                        User us = new User(r.getInt("id"), r.getString("email"), r.getString("name"), r.getString("password"),r.getString("teacher"), r.getString("userlessons"));
                        return us;
                    }
                }
            }
            return new User();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new User();
        }
    }
}
