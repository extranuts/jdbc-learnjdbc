package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

        String flightId = "2 OR 1 = 1"; //SQL Inject
        final var result = getTicketsByFlightId(flightId);
        System.out.println(result);


    }
    private static List<Long> getTicketsByFlightId(String flightId) throws SQLException {
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = %s
                """.formatted(flightId);
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             final var statement = connection.createStatement()) {
            final var resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                result.add(resultSet.getObject("id", Long.class)); //Null Safety
            }
        }
        return result;
    }
}
