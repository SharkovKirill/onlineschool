/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nsu_group.ui;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
//import java.util.concurrent.atomic.AtomicLong;
import java.sql.*;


/**
 * @author Dave Syer
 */
public class InMemoryLessonRespository implements LessonRepository {
    //	private final static String url = "jdbc:mysql://127.0.0.1:3306/onlineschool";
    private final static String url = "jdbc:mysql://localhost/onlineschool";
    private final static String user = "root";

//	private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Integer, Lesson> lessons = new ConcurrentHashMap<Integer, Lesson>();

    @Override
    public Iterable<Lesson> findAll() {

        try {
            Connection connection = DriverManager.getConnection(url, user, "root");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Lessons;");
//			resultSet.next();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Lesson lesson = new Lesson();
                lesson.setId(id);
                lesson.setName(resultSet.getString("name"));
                lesson.setDescription(resultSet.getString("description"));
                lesson.setVideo(resultSet.getString("video"));
                lesson.setCourse(resultSet.getString("course"));
                this.lessons.put(id, lesson);
            }
            connection.close();
            statement.close();
            return this.lessons.values();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return this.lessons.values();
        }
//		return this.lessons.values();
    }


    @Override
    public Lesson save(Lesson lesson) {
        try {
            Connection connection = DriverManager.getConnection(url, user, "root");
            Statement statement = connection.createStatement();

//            Formatter s = new Formatter();
//            s.format("SELECT * FROM Lessons WHERE id= (select max(id) from Lessons);");
//            ResultSet resultSet = statement.executeQuery(String.valueOf(s));
//            resultSet.next();
//            int id = resultSet.getInt("id") + 1;

            Formatter sq = new Formatter();
            sq.format("INSERT INTO `onlineschool`.`lessons` (`name`, `description`, `video`, `course`) VALUES ('%s', '%s', '%s', '%s');",
                    new String(lesson.getName().getBytes("ISO-8859-1"), "UTF-8"), new String(lesson.getDescription().getBytes("ISO-8859-1"), "UTF-8"), new String(lesson.getVideo().getBytes("ISO-8859-1"), "UTF-8"), new String(lesson.getCourse().getBytes("ISO-8859-1"), "UTF-8"));
            statement.executeUpdate(String.valueOf(sq));



            ResultSet array = statement.executeQuery("SELECT DISTINCT course FROM Lessons;");
            ArrayList<String> li = new ArrayList<String>();
            while(array.next()){
                li.add(array.getString("course"));
            }
            lesson.lis = li;
            System.out.println(li);

            connection.close();
            statement.close();
            return lesson;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return lesson;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return lesson;
        }
    }

    @Override
    public Lesson findLesson(int id) {
        try {
            Connection connection = DriverManager.getConnection(url, user, "root");
            Statement statement = connection.createStatement();

            String query = "SELECT name, description, video, course value FROM `lessons` WHERE id = '" + id + "';";
            ResultSet rs = statement.executeQuery(query);
            rs.next();

            Lesson lesson = new Lesson();
            lesson.setId(id);
            lesson.setName(rs.getString("name"));
            lesson.setDescription(rs.getString("description"));
            lesson.setVideo(rs.getString("video"));
            lesson.setCourse(rs.getString("course"));
            connection.close();
            statement.close();
            return lesson;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Lesson lesson = new Lesson();
            return lesson;

//		return this.lessons.get(id);
//	}finally {
////			return lessons
//		}

        }
    }

    @Override
    public Lesson testSave(HashMap<String, Object> model) {
        Lesson lesson = (Lesson) model.get("lesson");
        try {
            Connection connection = DriverManager.getConnection(url, user, "root");
            Statement statement = connection.createStatement();
            User user = (User) model.get("user");

            Formatter s = new Formatter();
            s.format("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='onlineschool';");
            ResultSet resultSet = statement.executeQuery(String.valueOf(s));

            int checker = 0;
            while (resultSet.next()) {
                if(resultSet.getString("TABLE_NAME").equals(user.getEmail())){
                    checker = 1;
                }
            }
            if(checker==0){
                Formatter newUserTable = new Formatter();
                newUserTable.format("CREATE TABLE `onlineschool`.`%s` (`id` INT NOT NULL AUTO_INCREMENT, `name` VARCHAR(45) NULL, `description` VARCHAR(45) NULL,`video` VARCHAR(45) NULL,`course` VARCHAR(45) NULL, PRIMARY KEY (`id`));", user.getEmail());
                statement.executeUpdate(String.valueOf(newUserTable));
            }


            Formatter sq = new Formatter();
            sq.format("INSERT INTO `onlineschool`.`%s` (`name`, `description`, `video`, `course`) VALUES ('%s', '%s', '%s', '%s');",
                    user.getEmail(), new String(lesson.getName().getBytes("ISO-8859-1"), "UTF-8"), new String(lesson.getDescription().getBytes("ISO-8859-1"), "UTF-8"), new String(lesson.getVideo().getBytes("ISO-8859-1"), "UTF-8"), new String(lesson.getCourse().getBytes("ISO-8859-1"), "UTF-8"));
            statement.executeUpdate(String.valueOf(sq));
//            ResultSet array = statement.executeQuery("SELECT DISTINCT course FROM Lessons;");
            Formatter allLessons = new Formatter();
            allLessons.format("INSERT INTO `onlineschool`.`lessons` (`name`, `description`, `video`, `course`) VALUES ('%s', '%s', '%s', '%s');",
                    new String(lesson.getName().getBytes("ISO-8859-1"), "UTF-8"), new String(lesson.getDescription().getBytes("ISO-8859-1"), "UTF-8"), new String(lesson.getVideo().getBytes("ISO-8859-1"), "UTF-8"), new String(lesson.getCourse().getBytes("ISO-8859-1"), "UTF-8"));
            statement.executeUpdate(String.valueOf(allLessons));


            connection.close();
            statement.close();
            return lesson;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return lesson;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return lesson;
        }
    }
}
