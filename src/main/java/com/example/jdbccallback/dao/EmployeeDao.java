package com.example.jdbccallback.dao;

import com.example.jdbccallback.ds.Employee;
import lombok.SneakyThrows;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
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
               this::mapEmployee
        );
    }

    class EmployeeAverageSalaryRowCallBackHandler implements RowCallbackHandler {

        double total;
        int count;

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            total += rs.getDouble("salary");
            count ++;
        }
        public double averageSalary() {
            return total / count;
        }
    }

    public double averageEmployeeSalaryRowCallBackHandler() {
        EmployeeAverageSalaryRowCallBackHandler obj =
                new EmployeeAverageSalaryRowCallBackHandler();
         jdbcTemplate.query("select salary from employee",
                obj);
         return obj.averageSalary();
    }

    class EmployeeAverageResultSetExtractor implements ResultSetExtractor<Double> {

        @Override
        public Double extractData(ResultSet rs) throws SQLException, DataAccessException {
            double total = 0;
            int count = 0;
            while (rs.next()) {
                total += rs.getDouble("salary");
                count ++;
            }
            return total / count;
        }
    }
    public  double averageDatabaseLevel() {
        return jdbcTemplate.queryForObject("select avg(salary) from employee", Double.class);
    }
    public double averageModernWay() {
        return jdbcTemplate.queryForList("select salary from employee",
                        Double.class)
                .stream()
                .mapToDouble(s -> s)
                .average().getAsDouble();
    }
    public Double averageEmployeeSalaryResultSetExtractor() {
        EmployeeAverageResultSetExtractor obj =
                new EmployeeAverageResultSetExtractor();
        return jdbcTemplate.query("select salary from employee",
                obj);

    }

    @SneakyThrows
    private Employee mapEmployee(ResultSet rs, int i) {
        return new Employee(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getDouble("salary")
        );
    }
}
