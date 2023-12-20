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

    private static final String LANGUAGE = "ru";
    private static final int EMPLOYEES_COUNT = 20;//0;
    private static final int EMPLOYEES_ASSESSMENTS_COUNT = 1;//0;
    private static final int EMPLOYEES_SCHEDULES_DAYS_BACK = 5; // Number of days ago for schedules generation
    private static final int WORK_START_TIME = 5; // Start of work at 5 AM o clock
    private static  int count_of_sicks = 0; // Number of sick notes
    private static int count_of_vacations = 0; // Number of vacations



    public static void main(String[] args) {

        int startHour = WORK_START_TIME;// the employee's starting hour
        int startMinute = 30; // minutes of the employee's start time
        int workPreBreakInHour = 4; // duration of work before break in hours
        int breakDuration = 30; // Duration of the break in minutes
        int workAfterBreakDuration = 240; // Duration of work after a break in minutes

        Faker faker = new Faker(Locale.forLanguageTag(LANGUAGE));

        Connection connection;
        try { // edit connection string, if the database user is different
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

            for (int employees_count = 0; employees_count < EMPLOYEES_COUNT; employees_count++) {
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
                } catch (SQLException e) {
                    log.error("SQL error: {}", e.getMessage());
                }

                // Get the generated employeeId for use in other tables
                int employeeId;
                try (var resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        employeeId = resultSet.getInt(1);
                        log.info("Added employee with id: {}", employeeId);

                        // Add vacation
                        LocalDate vacationEnd = LocalDate.now().minusDays(count_of_vacations * 10L );
                        LocalDate vacationStart = vacationEnd.minusDays(30);
                        // TODO - generate please random values
                        String insertVacationQuery = String.format("INSERT INTO employees_vacations (employeeId, startDate, endDate) VALUES (%d, '%s', '%s')",
                                employeeId, formatDate(Date.from(vacationStart.atStartOfDay(ZoneId.systemDefault()).toInstant()), "yyyy-MM-dd"),
                                formatDate(Date.from(vacationEnd.atStartOfDay(ZoneId.systemDefault()).toInstant()), "yyyy-MM-dd"));
                        statement.executeUpdate(insertVacationQuery);
                        log.info("Added vacation for employee with id: {} between {} and {}", employeeId, vacationStart, vacationEnd);

                        // Updating the count of vacations
                        count_of_vacations++;
                        if(count_of_vacations > EMPLOYEES_COUNT) {
                            count_of_vacations = 0;
                        }

                        // Add sick to `employees_sicks`
                        boolean isSicked = false;
                        LocalDate sickEnd = LocalDate.now().minusDays(count_of_sicks * 7L);
                        LocalDate sickStart = sickEnd.minusDays(5);
                        if (employeeId % 10 == 0) /* One in ten people get sick */ {
                            String insertSickQuery = String.format("INSERT INTO employees_sicks (employeeId, startDate, endDate) VALUES (%d, '%s', '%s')",
                                    employeeId, formatDate(Date.from(sickStart.atStartOfDay(ZoneId.systemDefault()).toInstant()), "yyyy-MM-dd"),
                                    formatDate(Date.from(sickEnd.atStartOfDay(ZoneId.systemDefault()).toInstant()), "yyyy-MM-dd"));
                            statement.executeUpdate(insertSickQuery);
                            isSicked = true;
                            log.info("Added sick paper for employee with id: {} between {} and {}", employeeId, sickStart, sickEnd);
                        }
                        // Updating the count of sticks
                        count_of_sicks++;
                        if(count_of_sicks > EMPLOYEES_COUNT/10) {
                            count_of_sicks = 0;
                        }

                        // Filling the table employees_assessments
                        // Generate a random number of months to subtract from the current date
                        Date currentDate = new Date();
                        LocalDate localCurrentDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        for (int j = 0; j < EMPLOYEES_ASSESSMENTS_COUNT; j++) {

                            // Generate date within up to - 2 months from the current date
                            int monthsToAddOrSubtract = faker.number().numberBetween(0, -2);

                            LocalDate assessmentLocalDate = localCurrentDate.plusMonths(monthsToAddOrSubtract);

                            //
                            if(!assessmentLocalDate.isBefore(vacationStart) && !assessmentLocalDate.isAfter(vacationEnd)){
                                log.info("Assessment date {} for employee id: {} - is in vacation time from {} to {} and ignored", assessmentLocalDate, employeeId, vacationStart, vacationEnd);
                                continue;
                            }
                            //
                            if(isSicked && !assessmentLocalDate.isBefore(sickStart) && !assessmentLocalDate.isAfter(sickEnd)){
                                log.info("Assessment date {} for employee id: {} - is in sick time from {} to {} and ignored", assessmentLocalDate, employeeId, sickStart, sickEnd);
                                continue;
                            }

                            Date assessmentDate = Date.from(assessmentLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                            int performanceRating = faker.number().numberBetween(1, 5);
                            int salesAnalysis = faker.number().numberBetween(100, 500);

                            // Filling the table employees_assessments with correct date
                            String insertAssessmentQuery = String.format("INSERT INTO employees_assessments (employeeId, assessmentDate, performanceRating, salesAnalysis) VALUES (%d, '%s', %d, %d)",
                                    employeeId, formatDate(assessmentDate, "yyyy-MM-dd"), performanceRating, salesAnalysis);
                            statement.executeUpdate(insertAssessmentQuery);
                            log.info("Added employee  with id: {} to employees_assessments", employeeId);
                        }

                        // Filling the table employees_schedules
                        for (int j = EMPLOYEES_SCHEDULES_DAYS_BACK; j > 0; j--) {
                            LocalDate backDaysDate = LocalDate.now().minusDays(j);
                            // Check if the day is Sunday
                            if (backDaysDate.getDayOfWeek().getValue() == 7) continue; // we don't work on Sundays

                            //
                            if(!backDaysDate.isBefore(vacationStart) && !backDaysDate.isAfter(vacationEnd)){
                                log.info("Schedules date {} for employee id: {} - is in vacation time from {} to {} and ignored", backDaysDate, employeeId, vacationStart, vacationEnd);
                                continue;
                            }
                            //
                            if(isSicked && !backDaysDate.isBefore(sickStart) && !backDaysDate.isAfter(sickEnd)){
                                log.info("Schedules date {} for employee id: {} - is in sick time from {} to {} and ignored", backDaysDate, employeeId, sickStart, sickEnd);
                                continue;
                            }

                            // Logic for calculating the start and end times
                            // Initialization of working time before break
                            Date workStart = Date.from(backDaysDate.atTime(startHour, startMinute).atZone(ZoneId.systemDefault()).toInstant());
                            Date workPreBreakEnd = Date.from(backDaysDate.atTime(startHour + workPreBreakInHour, startMinute).atZone(ZoneId.systemDefault()).toInstant());
                            String insertPreBreakWorkQuery = String.format("INSERT INTO employees_schedules (employeeId, shiftDate, shiftType, workStart, workEnd) VALUES (%d, '%s', %d, '%s', '%s')",
                                    employeeId, formatDate(workStart, "yyyy-MM-dd"), SHIFT_TYPE.WORK_BEFORE_BREAK.getValue(), formatDate(workStart), formatDate(workPreBreakEnd));
                            statement.executeUpdate(insertPreBreakWorkQuery);

                            // Initialization of break start and end time
                            Date breakStart = Date.from(workPreBreakEnd.toInstant().plusSeconds(60 * breakDuration));
                            Date breakEnd = Date.from(breakStart.toInstant().plusSeconds(60 * breakDuration));
                            String insertBreakQuery = String.format("INSERT INTO employees_schedules (employeeId, shiftDate, shiftType, workStart, workEnd) VALUES (%d, '%s', %d, '%s', '%s')",
                                    employeeId, formatDate(breakStart, "yyyy-MM-dd"), SHIFT_TYPE.BREAK.getValue(), formatDate(breakStart), formatDate(breakEnd));
                            statement.executeUpdate(insertBreakQuery);

                            // Initialization of start time after break and end of work
                            Date workAfterBreakStart = Date.from(breakEnd.toInstant().plusSeconds(60 * breakDuration));
                            Date workEnd = Date.from(workAfterBreakStart.toInstant().plusSeconds(60 * workAfterBreakDuration));
                            String insertAfterBreakWorkQuery = String.format("INSERT INTO employees_schedules (employeeId, shiftDate, shiftType, workStart, workEnd) VALUES (%d, '%s', %d, '%s', '%s')",
                                    employeeId, formatDate(workAfterBreakStart, "yyyy-MM-dd"), SHIFT_TYPE.WORK_AFTER_BREAK.getValue(), formatDate(workAfterBreakStart), formatDate(workEnd));
                            statement.executeUpdate(insertAfterBreakWorkQuery);

                            log.info("Added employee with id: {} to employees_schedules for day: {}", employeeId, backDaysDate);
                        }
                        // Updating the start time for the next employee
                        startHour++;
                        if (startHour > 13) {
                            // employees don't work nights
                            startHour = WORK_START_TIME;
                        }

                        // Generating access rights for the employee
                        String[] accessRights = {"employee", "cashier", "administrator", "superuser"};
                        String randomAccess = accessRights[faker.number().numberBetween(0, accessRights.length)];

                        String login = firstName.toLowerCase().substring(0,2) + lastName.toLowerCase().substring(0,2);
                        String passwordHash = String.valueOf(faker.random().hashCode());
                        // Generating JSON with access rights
                        String accessJSON = "{\"rights\": \"" + randomAccess + "\"}";

                        // Adding a record to employees_permissions
                        String insertPermissionQuery = String.format("INSERT INTO employees_permissions (employeeId, login, employeeInternalNumber, passwordHash, accessRights) VALUES (%d, '%s', %d, '%s', '%s')",
                                employeeId, login, employeeId, passwordHash, accessJSON);
                        statement.executeUpdate(insertPermissionQuery);
                        log.info("Added permissions for employee with id: {}", employeeId);





                    }



                } catch (SQLException e) {
                    log.error("SQL exception: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    private static final String[] EMPLOYEES_TABLES = {
            "`supermarket`.`employees_assessments`",
            "`supermarket`.`employees_schedules`",
            "`supermarket`.`employees_sicks`",
            "`supermarket`.`employees_vacations`",
            "`supermarket`.`employees_permissions`",
            "`supermarket`.`employees`"
    }; // always the last one to be deleted

    // Convert date to a string in MySQL format "yyyy-MM-dd HH:mm:ss" || "yyyy-MM-dd"
    private static String formatDate(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    private static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm");
    }

    private enum SHIFT_TYPE {
        WORK_BEFORE_BREAK(1), BREAK(2), WORK_AFTER_BREAK(3);
        private final int value;

        SHIFT_TYPE(int value) {
            this.value = value;
        }

        private int getValue() {
            return value;
        }
    }

}
