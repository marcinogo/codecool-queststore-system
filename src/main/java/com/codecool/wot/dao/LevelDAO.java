package com.codecool.wot.dao;

import com.codecool.wot.model.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LevelDAO {
    private static final LevelDAO INSTANCE = new LevelDAO();

    private List<Level> levels;

    private LevelDAO() {
        this.levels = new LinkedList<>();
        loadPersonsFromDatabase();
    }

    public static LevelDAO getInstance() {
        return INSTANCE;
    }

    public List<Level> read() {
        return this.levels;
    }

    private void loadPersonsFromDatabase() {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = createSelectPreparedStatement(con);
             ResultSet result = ps.executeQuery()) {

            while (result.next()) {
                Integer id = result.getInt("personId");
                String name = result.getString("name");
                String description = result.getString("description");
                Double coolcoinValue = result.getDouble("coolcoins_value");

                levels.add(new Level(id, name, description, coolcoinValue));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public Level getLevel(Integer id) {
        Level level = null;
        for (Level candidate : this.levels) {
            if (candidate.getId().equals(id)) {
                level = candidate;
            }
        }
        return level;
    }

    public Level getLevel(String name) {
        Level level = null;
        for (Level candidate : this.levels) {
            if (candidate.getName().equals(name)) {
                level = candidate;
            }
        }
        return level;
    }


    private PreparedStatement createSelectPreparedStatement(Connection con) throws SQLException {
        String query = "SELECT * FROM levels;";
        PreparedStatement ps = con.prepareStatement(query);

        return ps;
    }
}
