package cz.larkyy.llibrary.database.sql;

import cz.larkyy.llibrary.database.Database;
import org.bukkit.configuration.file.YamlConfiguration;

import java.sql.*;
import java.util.Map;

public class MySQL implements Database {
    private Connection conn;

    private final String HOST;
    private final Integer PORT;
    private final String USER;
    private final String PASSWORD;
    private final String DATABASE;
    private final String TABLE;
    private Map<String, Column> columns;
    private Column primaryKey;

    public MySQL(final YamlConfiguration config, final String path, Map<String, Column> columns, Column primaryKey) {

        HOST = config.getString(path+".host", "localhost");
        PORT = config.getInt(path+".port", 3306);
        DATABASE = config.getString(path+".database");
        TABLE = config.getString(path+".table", "defaultTable");
        USER = config.getString(path+".user", "root");
        PASSWORD = config.getString(path+".password");
        this.columns = columns;
        this.primaryKey = primaryKey;

        createConnection();
    }

    public MySQL(final String host, final int port, final String database, final String table, final String user, final String password, Map<String, Column> columns, Column primaryKey) {

        HOST = host;
        PORT = port;
        DATABASE = database;
        TABLE = table;
        USER = user;
        PASSWORD = password;
        this.columns = columns;
        this.primaryKey = primaryKey;

        createConnection();
    }

    private void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://"+HOST+":"+PORT+"/?useUnicode=true&characterEncoding=utf8&useSSL=false&verifyServerCertificate=false", USER, PASSWORD);

            final Statement stmt = conn.createStatement();

            String sql = "CREATE DATABASE IF NOT EXISTS "+DATABASE;
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS "+DATABASE+"."+TABLE+getColumnString();
            stmt.executeUpdate(sql);

            stmt.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private String getColumnString() {
        StringBuilder str = new StringBuilder();

        str.append(" (");

        for (Map.Entry<String,Column> pair : columns.entrySet()) {

            Column column = pair.getValue();
            str
                    .append(pair.getKey())
                    .append(" ")
                    .append(column.getType())
                    .append(" ");

            if (!column.isCanBeNull()) {
                str
                        .append("NOT NULL");
            }

            str.append(", ");
        }
        str
                .append("PRIMARY KEY ( ")
                .append(primaryKey.getName())
                .append(" ))");

        return str.toString();
    }


    @Override
    public void setState(String uuid, String state) {

        try {
            if (conn.isClosed()) createConnection();

            final PreparedStatement stmt = conn.prepareStatement("INSERT INTO "+DATABASE+"."+TABLE+" (player, state) VALUES (?, ?) ON DUPLICATE KEY UPDATE state=?");
            stmt.setString(1, uuid);
            stmt.setString(2, state);
            stmt.setString(3, state);

            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getState(String uuid) {
        try {
            if (conn.isClosed()) createConnection();

            final PreparedStatement stmt = conn.prepareStatement("SELECT * FROM "+DATABASE+"."+TABLE+" WHERE player=?");
            stmt.setString(1, uuid);

            if (!stmt.execute()) return null;

            ResultSet results = stmt.getResultSet();

            while (results.next()) {
                if (results.getString("state") == null) continue;

                return results.getString("state");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close () {
        try {
            if (!conn.isClosed()) conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
