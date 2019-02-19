import auth.Database;

import java.io.IOException;
import java.sql.*;

class SqlHandler {
    String handleSQL(String SQLstring) {
        try {
            Connection connection = Database.getDatabaseConnection();
            System.out.println("Connected.");

            ResultSet resultSet = sendSQL(connection, SQLstring);

            if (resultSet == null) return "{}";

            return sqlToJson(resultSet);

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not connect to server. Wrong password? " +
                    "Delete creds0.dat and restart to reenter database password.");
            return "Could not connect to server. Server might not have been configured correctly.";
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Encountered an IO error.");
            return null;
        }
    }

    private ResultSet sendSQL(Connection connection, String sqlitis) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(sqlitis);
        return statement.getResultSet();
    }

    /**
     * Parses a ResultSet in order to obtain a JSON string
     * Relies on T-SQL's AUTO JSON functionality
     *
     * @param rs Response from a sql request
     * @return String
     * @throws SQLException May result from invalid SQL result set
     */
    private String sqlToJson(ResultSet rs) throws SQLException {
        if (rs == null) return "Statement prod";
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        StringBuilder sb = new StringBuilder();

        while (rs.next()) {
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                sb.append(rs.getString(i));
            }
        }

        return sb.toString();
    }
}
