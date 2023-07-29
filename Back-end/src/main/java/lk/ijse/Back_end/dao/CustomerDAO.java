package lk.ijse.Back_end.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
   /* Connection connection = DbConnection.getConnection().openSession();

    public boolean save(Student student) throws SQLException {
        PreparedStatement statement = null;
        statement = connection.prepareStatement("insert into Student values (?,?,?,?,?)");
        statement.setString(1, student.getId());
        statement.setString2, student.getName());
        statement.setString(3, student.getCity());
        statement.setString(4, student.getEmail());
        statement.setInt(5, student.getLevel());
        if (statement.executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Student search(String studentId) throws SQLException {
        PreparedStatement statement = null;

        statement = connection.prepareStatement("select * from Student where id = ?");
        statement.setString(1, studentId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Student student = new Student(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5)

            );
            return student;
        }
        return null;
    }

    public boolean update(Student student) throws SQLException {
        PreparedStatement statement = null;

        statement = connection.prepareStatement
                ("update Student s set s.name =?, s.city = ?, s.email = ?, s.level = ? where s.id = ? ");
        statement.setString(1, student.getName());
        statement.setString(2, student.getCity());
        statement.setString(3, student.getEmail());
        statement.setString(4, student.getId());
        if (statement.executeUpdate() > 0) {
            return true;
        } else {
            throw new SQLException();
        }
    }

    public boolean delete(String studentId) throws SQLException {
        PreparedStatement statement = null;

        statement = connection.prepareStatement("delete from Student where id = ?");
        statement.setString(1, studentId);
        if (statement.executeUpdate() > 0) {
            return true;
        } else {
            throw new SQLException();
        }

    }

    public List<Student> getAll() throws SQLException {

        List<Student> studentArrayList = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from student");
        int i = 0;
        while (resultSet.next()) {
            i++;
            Student student = new Student(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5)
            );
            studentArrayList.add(student);
        }
        return studentArrayList;

    }
*/

}
