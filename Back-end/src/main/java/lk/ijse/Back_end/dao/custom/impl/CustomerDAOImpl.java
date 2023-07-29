package lk.ijse.Back_end.dao.custom.impl;

import lk.ijse.Back_end.dao.custom.CustomerDAO;
import lk.ijse.Back_end.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public void save(Customer customer, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into customer values (?,?,?,?);");
        statement.setObject(1, customer.getCustID());
        statement.setObject(2, customer.getCustName());
        statement.setObject(3, customer.getCustAddress());
        statement.setObject(4, customer.getCustSalary());
        if (statement.executeUpdate() > 0) {
            System.out.println("Saved");
        } else {
            System.out.println("Saved Failed");
        }
    }

    @Override
    public void update(Customer customer, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update customer set name=?,address=?,salary=? where id=? ");
        statement.setObject(1, customer.getCustName());
        statement.setObject(2, customer.getCustAddress());
        statement.setObject(3, customer.getCustSalary());
        statement.setObject(4, customer.getCustID());
        if (statement.executeUpdate() > 0) {
            System.out.println("Updated");
        } else {
            System.out.println("Update Failed");
        }
    }

    @Override
    public void delete(String id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM customer WHERE id=?;");
        statement.setObject(1, id);
        if (statement.executeUpdate() > 0) {
            System.out.println("Deleted");
        } else {
            System.out.println("Delete Failed");
        }
    }

    @Override
    public Customer search(String id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from customer where id=?");
        statement.setObject(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        throw new SQLException("Customer not found");
    }

    @Override
    public List<Customer> getAll(Connection connection) throws SQLException {
        ResultSet resultSet = connection.prepareStatement("select * from customer").executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (resultSet.next()) {
            customers.add(
                    new Customer(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4)
                    )
            );
        }
        return customers;
    }

    @Override
    public String generateNextId(Connection connection) throws SQLException {
        ResultSet resultSet = connection.prepareStatement("SELECT id FROM customer ORDER BY id DESC limit 1").executeQuery();
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            int i = Integer.parseInt(lastId.replace("C00-", "")) + 1;
            return String.format("C00-%03d", i);
        } else {
            return "C00-001";
        }
    }

    @Override
    public boolean isExist(String id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from customer where id=?");
        statement.setObject(1, id);
        return statement.executeQuery().next();
    }
}
