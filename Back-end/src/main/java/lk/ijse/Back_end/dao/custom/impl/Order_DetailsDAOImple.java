package lk.ijse.Back_end.dao.custom.impl;

import lk.ijse.Back_end.dao.custom.Order_DetailsDAO;
import lk.ijse.Back_end.entity.OrderDetails;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Order_DetailsDAOImple implements Order_DetailsDAO {
    @Override
    public void save(OrderDetails orderDetails, Connection connection) throws SQLException {
        String sql = "INSERT INTO order_details (order_id, item_code, qty, unit_price, total) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, orderDetails.getOrder_Id());
        statement.setString(2, orderDetails.getOrItemCOde());
        statement.setInt(3, orderDetails.getOrItemQTY());
        statement.setObject(4, orderDetails.getOrItemPrice());
        statement.setObject(5, orderDetails.getOrItemTotal());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void update(OrderDetails orderDetails, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement
                ("UPDATE order_details SET item_code = ?, qty = ?, unit_price = ?, total = ? WHERE id = ?");
        statement.setString(1, orderDetails.getOrItemCOde());
        statement.setInt(2, orderDetails.getOrItemQTY());
        statement.setObject(3, orderDetails.getOrItemPrice());
        statement.setObject(4, orderDetails.getOrItemTotal());
        statement.setInt(5, orderDetails.getId());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void delete(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM order_details WHERE id = ?");
        statement.setObject(1, id);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public OrderDetails search(Integer id, Connection connection) throws SQLException {
        OrderDetails orderDetails = null;
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM order_details WHERE id = ?");
        statement.setObject(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            orderDetails = new OrderDetails(
                    resultSet.getInt("id"),
                    resultSet.getString("order_id"),
                    resultSet.getString("item_code"),
                    resultSet.getInt("qty"),
                    resultSet.getDouble("unit_price"),
                    resultSet.getDouble("total")
            );
        }
        resultSet.close();
        statement.close();
        return orderDetails;
    }

    @Override
    public List<OrderDetails> getAll(Connection connection) throws SQLException {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM order_details");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            orderDetailsList.add(
                    new OrderDetails(
                            resultSet.getInt("id"),
                            resultSet.getString("order_id"),
                            resultSet.getString("item_code"),
                            resultSet.getInt("qty"),
                            resultSet.getDouble("unit_price"),
                            resultSet.getDouble("total")
                    )
            );
        }
        return orderDetailsList;
    }

    @Override
    public String generateNextId(Connection connection) throws SQLException {
        ResultSet resultSet = connection.prepareStatement("SELECT code FROM item ORDER BY code DESC limit 1").executeQuery();
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            int i = Integer.parseInt(lastId.replace("O", "")) + 1;
            return String.format("O%03d", i);
        } else {
            return "O001";
        }
    }

    @Override
    public boolean isExist(Integer id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("Select * FROM order_details WHERE id = ?");
        statement.setInt(1, id);
        return statement.executeQuery().next();
    }

    @SneakyThrows
    @Override
    public List<OrderDetails> getOrderDetailsByOrderId(String orderId, Connection connection) {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM order_details where order_id=?");
        statement.setObject(1,orderId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            orderDetailsList.add(
                    new OrderDetails(
                            resultSet.getInt("id"),
                            resultSet.getString("order_id"),
                            resultSet.getString("item_code"),
                            resultSet.getInt("qty"),
                            resultSet.getDouble("unit_price"),
                            resultSet.getDouble("total")
                    )
            );
        }
        return orderDetailsList;
    }
}
