package lk.ijse.Back_end.dao.custom.impl;

import lk.ijse.Back_end.dao.custom.OrderDAO;
import lk.ijse.Back_end.entity.Item;
import lk.ijse.Back_end.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public void save(Order order, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO `order` VALUES (?, ?, ?, ?, ?)");
        statement.setString(1, order.getOId());
        statement.setString(2, order.getCustId());
        ((PreparedStatement) statement).setDate(3, new Date(order.getDate().getTime())); // Assuming 'Date' property is of type java.util.Date
        statement.setDouble(4, order.getDiscount());
        statement.setDouble(5, order.getSubtotal());
        statement.executeUpdate();
        statement.close();

    }

    @Override
    public void update(Order order, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement
                ("UPDATE `order` SET custId = ?, date = ?, discount = ?, sub_total = ? WHERE oId = ?");
        statement.setString(1, order.getCustId());
        statement.setDate(2, new Date(order.getDate().getTime()));
        statement.setDouble(3, order.getDiscount());
        statement.setDouble(4, order.getSubtotal());
        statement.setString(5, order.getOId());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void delete(String id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM `order` WHERE oId = ?");
        statement.setString(1, id);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public Order search(String id, Connection connection) throws SQLException {
        Order item = null;
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `order` WHERE oId = ?");
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String custId = resultSet.getString("custId");
            Date date = resultSet.getDate("date");
            double discount = resultSet.getDouble("discount");
            double subtotal = resultSet.getDouble("sub_total");
            item = new Order(id, custId, date, discount, subtotal);
        }
        resultSet.close();
        statement.close();
        return item;
    }

    @Override
    public List<Order> getAll(Connection connection) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM `order`";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String oId = resultSet.getString("oId");
            String custId = resultSet.getString("custId");
            Date date = resultSet.getDate("date");
            double discount = resultSet.getDouble("discount");
            double subtotal = resultSet.getDouble("sub_total");
            Order order = new Order(oId, custId, date, discount, subtotal);
            orders.add(order);
        }
        resultSet.close();
        statement.close();
        return orders;
    }

    @Override
    public String generateNextId(Connection connection) throws SQLException {
        ResultSet resultSet = connection.prepareStatement("SELECT oId FROM `order` ORDER BY oId DESC limit 1").executeQuery();
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            int i = Integer.parseInt(lastId.replace("PO-", "")) + 1;
            return String.format("PO-%03d", i);
        } else {
            return "PO-001";
        }
    }

    @Override
    public boolean isExist(String id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from `order` where oId=?");
        statement.setObject(1, id);
        return statement.executeQuery().next();
    }
}
