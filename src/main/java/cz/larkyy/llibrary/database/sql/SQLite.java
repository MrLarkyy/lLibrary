package cz.larkyy.llibrary.database.sql;

import cz.larkyy.llibrary.database.Database;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.Map;

public class SQLite implements Database {

    private JavaPlugin plugin;

    private Connection conn;
    private final String TABLE;
    private Map<String, Column> columns;
    private Column primaryKey;

    public SQLite (final JavaPlugin plugin,String table, Map<String, Column> columns, Column primaryKey) {
        this.TABLE = table;
        this.plugin = plugin;
        this.columns = columns;
        this.primaryKey = primaryKey;

        createConnection();

    }

    private void createConnection () {
        try {

            conn = DriverManager.getConnection("jdbc:sqlite:"+plugin.getDataFolder()+"/data.db");

            final Statement stmt = conn.createStatement();

            final String sql = "CREATE TABLE IF NOT EXISTS "+TABLE+getColumnString();
            stmt.executeUpdate(sql);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setState(String uuid, String state) {
        try {
            if (conn.isClosed()) createConnection();

            final PreparedStatement stmt = conn.prepareStatement("INSERT OR REPLACE INTO HideItem (player, state) VALUES (?, ?)");

            stmt.setString(1, uuid);
            stmt.setString(2, state);

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

            final PreparedStatement stmt = conn.prepareStatement("SELECT * FROM HideItem WHERE player=?");
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
        }
        str
                .append("PRIMARY KEY ( ")
                .append(primaryKey.getName())
                .append(" ))");

        return str.toString();
    }

}
