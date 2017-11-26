package com.codecool.wot.dao;

import com.codecool.wot.model.Account;
import com.codecool.wot.model.Mentor;
import com.codecool.wot.model.SchoolClass;
import com.codecool.wot.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ClassDAO {
    private static final ClassDAO INSTANCE = new ClassDAO();

    private List<SchoolClass> classes;

    private ClassDAO() {
        this.classes = new LinkedList<>();
        loadClassesFromDatabase();
    }

    public static ClassDAO getInstance() {
        return INSTANCE;
    }

    public List<SchoolClass> read() {
        return this.classes;
    }

    public void add(SchoolClass schoolClass) {
        try {
            addClassToDatabase(schoolClass);
            this.classes.add(schoolClass);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void update(SchoolClass schoolClass) {
        try {
            updateClassInDatabase(schoolClass);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void remove(SchoolClass schoolClass) {
        try {
            deleteAllPersonsFromClassInMemory(schoolClass);
            deleteAllPersonsFromClassInDatabase(schoolClass);
            deleteClassFromDatabase(schoolClass);
            this.classes.remove(schoolClass);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public SchoolClass getClass(Integer classId) {
        SchoolClass schoolClass = null;
        for (SchoolClass candidate : this.classes) {
            if (candidate.getId().equals(classId)) {
                schoolClass = candidate;
            }
        }
        return schoolClass;
    }

    public SchoolClass getClass(String className) {
        SchoolClass schoolClass = null;
        for (SchoolClass candidate : this.classes) {
            if (candidate.getName().equals(className)) {
                schoolClass = candidate;
            }
        }
        return schoolClass;
    }

    public void addPerson(SchoolClass schoolClass, Account person) {
        try {
            addPersonToClassInDatabase(schoolClass, person);
            schoolClass.assignPerson(person);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void addPersonToMemory(SchoolClass schoolClass, Account person) {
        schoolClass.assignPerson(person);
    }

    public void removePerson(SchoolClass schoolClass, Account person) {
        try {
            removePersonFromClassInDatabase(schoolClass, person);
            schoolClass.removePerson(person);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void loadClassesFromDatabase() {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = createSelectPreparedStatement(con);
             ResultSet result = ps.executeQuery()) {

            while (result.next()) {
                Integer classId = result.getInt("classId");
                String name = result.getString("name");

                classes.add(new SchoolClass(classId, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void addClassToDatabase(SchoolClass schoolClass) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = createAddPreparedStatement(con, schoolClass)) {
            con.setAutoCommit(false);
            ps.executeUpdate();
            con.commit();
        }
    }

    private void updateClassInDatabase(SchoolClass schoolClass) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = createUpdatePreparedStatement(con, schoolClass)) {
            con.setAutoCommit(false);
            ps.executeUpdate();
            con.commit();
        }
    }

    private void deleteClassFromDatabase(SchoolClass schoolClass) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = createDeletePreparedStatement(con, schoolClass)) {
            con.setAutoCommit(false);
            ps.executeUpdate();
            con.commit();
        }
    }

    private void deleteAllPersonsFromClassInDatabase(SchoolClass schoolClass) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = createDeleteAllPersonsPreparedStatement(con, schoolClass)) {
            con.setAutoCommit(false);
            ps.executeUpdate();
            con.commit();
        }
    }

    private void deleteAllPersonsFromClassInMemory(SchoolClass schoolClass) {
        for (Account person: PersonDAO.getInstance().read()) {
            if (person instanceof Mentor) {
                Mentor mentor = (Mentor) person;
                if (mentor.getSchoolClass().equals(schoolClass)) {
                    mentor.setSchoolClass();
                }
            } else if (person instanceof Student) {
                Student student = (Student) person;
                if (student.getSchoolClass().equals(schoolClass)) {
                    student.setSchoolClass();
                }
            }
        }
    }

    private void addPersonToClassInDatabase(SchoolClass schoolClass, Account person) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = createAddPersonPreparedStatement(con, schoolClass, person)) {
            con.setAutoCommit(false);
            ps.executeUpdate();
            con.commit();
        }
    }

    private void removePersonFromClassInDatabase(SchoolClass schoolClass, Account person) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = createDeletePersonPreparedStatement(con, schoolClass, person)) {
            con.setAutoCommit(false);
            ps.executeUpdate();
            con.commit();
        }
    }

    private PreparedStatement createSelectPreparedStatement(Connection con) throws SQLException {
        String query = "SELECT * FROM classes;";
        PreparedStatement ps = con.prepareStatement(query);

        return ps;
    }

    private PreparedStatement createSelectPersonsPreparedStatement(Connection con) throws SQLException {
        String query = "SELECT * FROM persons_classes;";
        PreparedStatement ps = con.prepareStatement(query);

        return ps;
    }

    private PreparedStatement createAddPreparedStatement(Connection con, SchoolClass schoolClass) throws SQLException {
        String query = "INSERT INTO classes (name) VALUES (?);";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, schoolClass.getName());

        return ps;
    }

    private PreparedStatement createUpdatePreparedStatement(Connection con, SchoolClass schoolClass) throws SQLException {
        String query = "UPDATE classes SET name = ? WHERE classId = ?;";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, schoolClass.getName());
        ps.setInt(2, schoolClass.getId());

        return ps;
    }

    private PreparedStatement createDeletePreparedStatement(Connection con, SchoolClass schoolClass) throws SQLException {
        String query = "DELETE FROM classes WHERE classId = ?;";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, schoolClass.getId());

        return ps;
    }

    private PreparedStatement createDeleteAllPersonsPreparedStatement(Connection con, SchoolClass schoolClass) throws SQLException {
        String query = "DELETE FROM persons_classes WHERE classId = ?;";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, schoolClass.getId());

        return ps;
    }

    private PreparedStatement createAddPersonPreparedStatement(Connection con, SchoolClass schoolClass, Account person) throws SQLException {
        String query = "INSERT INTO persons_classes (personId, classId) VALUES (?, ?);";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, person.getId());
        ps.setInt(2, schoolClass.getId());

        return ps;
    }

    private PreparedStatement createDeletePersonPreparedStatement(Connection con, SchoolClass schoolClass, Account person) throws SQLException {
        String query = "DELETE FROM persons_classes WHERE personId = ? AND classId = ?;";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, person.getId());
        ps.setInt(2, schoolClass.getId());

        return ps;
    }
}
