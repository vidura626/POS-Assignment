package lk.ijse.Back_end.dao.custom.impl;

import lk.ijse.Back_end.dao.custom.ItemDAO;
import lk.ijse.Back_end.entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {


    @Override
    public void save(Item item, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO item VALUES (?, ?, ?, ?)");
        statement.setString(1, item.getItemCode());
        statement.setString(2, item.getItemName());
        statement.setInt(3, item.getItemQty());
        statement.setDouble(4, item.getItemUnitPrice());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void update(Item item, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE item SET name = ?, unit_price = ?, qty = ? WHERE code = ?");
        statement.setString(1, item.getItemName());
        statement.setDouble(2, item.getItemUnitPrice());
        statement.setInt(3, item.getItemQty());
        statement.setString(4, item.getItemCode());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void delete(String id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM item WHERE code = ?");
        statement.setObject(1, id);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public Item search(String id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from item where code=?");
        statement.setObject(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            );
        }
        throw new SQLException("Item not found");
    }

    @Override
    public List<Item> getAll(Connection connection) throws SQLException {
        ResultSet resultSet = connection.prepareStatement("select * from item").executeQuery();
        List<Item> items = new ArrayList<>();
        while (resultSet.next()) {
            items.add(
                    new Item(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getDouble(4)
                    )
            );
        }
        return items;
    }

    @Override
    public String generateNextId(Connection connection) throws SQLException {
        ResultSet resultSet = connection.prepareStatement("SELECT code FROM item ORDER BY code DESC limit 1").executeQuery();
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            int i = Integer.parseInt(lastId.replace("I00-", "")) + 1;
            return String.format("I00-%03d", i);
        } else {
            return "I00-001";
        }
    }

    @Override
    public boolean isExist(String id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from item where code=?");
        statement.setObject(1, id);
        return statement.executeQuery().next();
    }
}
