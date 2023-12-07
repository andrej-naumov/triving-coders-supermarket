package org.thriving.coders.supermarket.util.mysql;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

/**
 * All tables related to employees are filled with NEW random data
 */
@Slf4j
public class ResetEmployeesTables {
    private static final int EMPLOYEES_COUNT = 10;
    private static final String LANGUAGE = "ru";

    private static final String[] EMPLOYEES_TABLES = {
            "`supermarket`.`employees_assessments`",
            "`supermarket`.`employees_schedules`",
            "`supermarket`.`employees_sicks`",
            "`supermarket`.`employees_vacations`",
            "`supermarket`.`employees`"
    }; // always the last one to be deleted

    public static void main(String[] args) {
        Faker faker = new Faker(new Locale(LANGUAGE));

        Connection connection = null;
        try { // edit connection string
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarket", "supermarket", "6zv7ss@QfeI37jTz");
        } catch (SQLException e) {
            log.error("Not connected to database: {}", e.getSQLState());
            throw new RuntimeException(e);
        }

        try {
            Statement statement = connection.createStatement();

            String deleteFromTable = "DELETE FROM %s;";
            // Delete from employees tables
            for (String employeesTable : EMPLOYEES_TABLES) {
                String query = String.format(deleteFromTable, employeesTable);
                statement.execute(query);
                log.info("All data deleted from table: {}", employeesTable);
            }

            for (int i = 0; i < EMPLOYEES_COUNT; i++) {
            // Filling the table employees
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                String position = faker.job().position();
                int department = faker.number().numberBetween(1, 5);
                String contactInfo = faker.phoneNumber().phoneNumber();
                int hourlyRate = faker.number().numberBetween(10, 50);
                String commentary = faker.funnyName().name();

                String insertEmployeeQuery = String.format("INSERT INTO employees (firstName, lastName, position, department, contactInfo, hourlyRate, commentary) VALUES ('%s', '%s', '%s', %d, '%s', %d, '%s')",
                        firstName, lastName, position, department, contactInfo, hourlyRate, commentary);
                try {
                    statement.executeUpdate(insertEmployeeQuery, Statement.RETURN_GENERATED_KEYS);
                }catch (java.sql.SQLException e) {
                    log.error("SQL error: {}", e.getMessage());
                }



                // Get the generated employeeId for use in other tables
                int employeeId;
                try (var resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        employeeId = resultSet.getInt(1);
                        log.info("Added employee with id: {}", employeeId);

                        // Filling the table employees_assessments
                        // Generate date within up to - 2 months from the current date
                        Date currentDate = new Date();
                        LocalDate localCurrentDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        // Generate a random number of months to subtract from the current date
                        int monthsToAddOrSubtract = faker.number().numberBetween(0, -2);

                        LocalDate generatedDate = localCurrentDate.plusMonths(monthsToAddOrSubtract);
                        Date assessmentDate = Date.from(generatedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                        // Convert date to MySQL format
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate = dateFormat.format(assessmentDate);

                        int performanceRating = faker.number().numberBetween(1, 5);
                        int salesAnalysis = faker.number().numberBetween(100, 500);

                        // Filling the table employees_assessments with correct date
                        String insertAssessmentQuery = String.format("INSERT INTO employees_assessments (employeeId, assessmentDate, performanceRating, salesAnalysis) VALUES (%d, '%s', %d, %d)",
                                employeeId, formattedDate, performanceRating, salesAnalysis);
                        statement.executeUpdate(insertAssessmentQuery);
                        log.info("Added employee  with id: {} to employees_assessments", employeeId);





                    } else {
                        log.error("Error receiving a generated key",  new SQLException());
                    }
                }



            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // TODO ? use or not ?
            // log.info("finally block unused");
        }
    }



}
