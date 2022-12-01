package com.example.jdbccallback.dao;

import com.example.jdbccallback.ds.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDao {

    private JdbcTemplate jdbcTemplate;

    public EmployeeDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
    }

    public List<String> listEmail() {
        return jdbcTemplate.queryForList("select email from employee", String.class);
    }

    public List<Employee> listEmployee() {
        return jdbcTemplate.query("select * from employee",
                new RowMapper<Employee>() {
                    @Override
                    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {

                        return new Employee(
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("email")
                        );
                    }
                });
    }
}
