package ru.asanov.dao;

import org.springframework.stereotype.Component;
import ru.asanov.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_ID = 0;
    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "qwertyuiop123";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load PostgreSQL JDBC driver", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = "select * from person";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    public Person getPersonById(int id) {
        Person person = null;
        try {
            Connection connection = getConnection();
            PreparedStatement statement
                    = connection.prepareStatement(
                    "select * from person where id = ?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    public void save(Person person) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement
                    = connection.prepareStatement(
                    "insert into person(id, name) values(?, ?)"
            );
            preparedStatement.setInt(1, ++PEOPLE_ID);
            preparedStatement.setString(2, person.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, Person person) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement
                    = connection.prepareStatement("UPDATE person SET name = ? WHERE id = ?");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM person WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
