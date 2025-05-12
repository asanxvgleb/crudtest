package ru.asanov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.asanov.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static int PEOPLE_ID = 0;

    public List<Person> index() {
      return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Person getPersonById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE id = ?", new PersonMapper(), id);
    }

    public void save(Person person) {
       jdbcTemplate.update("INSERT INTO Person VALUES (?, ?)", person.getId(), person.getName());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name = ? WHERE id = ?", person.getName(), id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }
}
