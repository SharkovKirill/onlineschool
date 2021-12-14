package nsu_group.ui;

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
    private final static String bduser = "root";

    private final ArrayList<User> users = new ArrayList<User>();

    @Override
    public ArrayList<User> findAll() {
        return null;
    }

    @Override
    public User save(User user) {
        try {
            Connection connection = DriverManager.getConnection(url, bduser, "root");
            Statement statement = connection.createStatement();

//            Formatter s = new Formatter();
//            s.format("SELECT * FROM Users WHERE id= (select max(id) from Users);");
//            ResultSet resultSet = statement.executeQuery(String.valueOf(s));
//            resultSet.next();
//            int id = resultSet.getInt("id") + 1;

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
    public User check(User user) {
        try {
            Connection connection = DriverManager.getConnection(url, bduser, "root");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users;");
//			resultSet.next();
            while (resultSet.next()) {

                if(user.getEmail().equals(resultSet.getString("email")) & user.getPassword().equals(resultSet.getString("password"))){
                    return user;
                }
            }
            connection.close();
            statement.close();
            return null; ///надо понять что нужно возвращать
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null; ///надо понять что нужно возвращать
        }
    }


    @Override
    public User findUser(int id) {
        return null;
    }
}
