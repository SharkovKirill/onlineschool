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

import java.util.Formatter;
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

			ResultSet resultSet = statement.executeQuery("SELECT * FROM Lessons");
//			resultSet.next();
			while (resultSet.next()){
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

			Formatter s = new Formatter();
			s.format("SELECT * FROM Lessons WHERE id= (select max(id) from Lessons);");
			ResultSet resultSet = statement.executeQuery(String.valueOf(s));
			resultSet.next();
			int id = resultSet.getInt("id") + 1;

			Formatter sq = new Formatter();
			sq.format("INSERT INTO `onlineschool`.`lessons` (`id`, `name`, `description`, `video`, `course`) VALUES ('%s', '%s', '%s', '%s', '%s');",
					id, lesson.getName(), lesson.getDescription(), lesson.getVideo(), lesson.getCourse());
			statement.executeUpdate(String.valueOf(sq));
			connection.close();
			statement.close();
			return lesson;

		} catch (SQLException throwables) {
			throwables.printStackTrace();
			return lesson;
		}
	}

	@Override
	public Lesson findLesson(int id) {
		try {
			Connection connection = DriverManager.getConnection(url, user, "root");
			Statement statement = connection.createStatement();

			String query = "SELECT name, description, video, course value FROM `lessons` WHERE id = '"+id+"';";
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
}}
