package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
//
//        Long flightId = 2L; //SQL Inject
//        final var result = getTicketsByFlightId(flightId);
//        System.out.println(result);
        var result = getFlightsBetween(LocalDate.of(2020, 10, 1).atStartOfDay(), LocalDateTime.now());
        System.out.println(result);
    }

    private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = """
                Select id
                from flight
                where departure_date BETWEEN ? AND ?
                """;
        List<Long> result = new ArrayList<>();

        try (var connection = ConnectionManager.open();
            var preparedStatement = connection.prepareStatement(sql)) {
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(preparedStatement);

            final var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result.add(resultSet.getLong("id"));
            }
        }
        return result;

    }

    private static List<Long> getTicketsByFlightId(Long flightId) throws SQLException {
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = ? 
                """;
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             final var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, flightId);
            final var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getObject("id", Long.class)); //Null Safety
            }
        }
        return result;
    }
}
