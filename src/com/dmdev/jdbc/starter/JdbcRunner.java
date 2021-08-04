package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

        Long flightId = 2L; //SQL Inject
        final var result = getTicketsByFlightId(flightId);
        System.out.println(result);
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
