//package com.codecool.wot.dao.archive;
//
//import com.codecool.wot.dao.AbstractCodecoolerDAO;
//import com.codecool.wot.model.Mentor;
//
//import java.sql.*;
//
//
//public class MentorDAO extends AbstractCodecoolerDAO<Mentor> {
//
//    private Connection connection;
//
//    public MentorDAO(Connection connection) {
//        this.connection = connection;
//        loadMentors();
//    }
//
//    @Override
//    public void add(Mentor object) {
//        super.add(object);
//        saveToDataBase(object);
//    }
//
//    private void loadMentors() {
//
//        try {
//            connection.setAutoCommit(false);
//            Statement stmt = connection.createStatement();
//
//            String query = "SELECT * FROM persons JOIN persons_classes ON persons_classes.personId = persons.personId WHERE role ='mentor'";
//
//            ResultSet rs = stmt.executeQuery(query);
//
//            while (rs.next()) {
//
//                Integer Id = rs.getInt("personId");
//                String name = rs.getString("name");
//                String surname = rs.getString("surname");
//                String email = rs.getString("email");
//                String login = rs.getString("login");
//                String password = rs.getString("password");
//                Integer classId = rs.getInt("classId");
//
//                objectsList.add(new Mentor(name, surname, email, login, password, Id, classId));
//            }
//
//            rs.close();
//            stmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    private void saveToDataBase(Mentor mentor) {
//
//        try {
//            System.out.println(mentor.getId());
//            Statement stmt = connection.createStatement();
//
//            String values = String.format(" ('%d', '%s', '%s', '%s', '%s', '%s', '%s', 'mentor');", mentor.getId(), mentor.getName(), mentor.getSurname()
//                    , mentor.getEmail(), "0000", mentor.getLogin(), mentor.getPassword());
//
//
//
//            String values2 = String.format("('%d', '%d');", mentor.getId(), mentor.getSchoolClass().getId());
//
//            String query1 = "INSERT INTO persons (personId, name, surname, email,  phone, login, password, role) VALUES " + values;
//
//            String query2 = "INSERT INTO persons_classes (personId, classId) VALUES " + values2;
//
//            stmt.executeUpdate(query1);
//            stmt.executeUpdate(query2);
//            stmt.close();
//            connection.commit();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void updateMentor(Mentor mentor) {
//
//        try {
//            Statement stmt = connection.createStatement();
//
//            String query = String.format("UPDATE persons SET name = '%s', surname = '%s', login = '%s', password = '%s'	WHERE personId = %d ;",
//                    mentor.getName(), mentor.getSurname(), mentor.getLogin(), mentor.getPassword(), mentor.getId());
//
//            stmt.executeUpdate(query);
//
//            stmt.close();
//            connection.commit();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
