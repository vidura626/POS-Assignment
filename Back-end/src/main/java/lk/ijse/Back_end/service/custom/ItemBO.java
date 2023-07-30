package lk.ijse.Back_end.service.custom;

import lk.ijse.Back_end.dto.ItemDTO;
import lk.ijse.Back_end.service.SuperBO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    void saveItem(ItemDTO ItemDTO, Connection connection) throws SQLException;

    void updateItem(ItemDTO ItemDTO, Connection connection) throws SQLException;

    void deleteItem(String id, Connection connection) throws SQLException;

    ItemDTO searchItem(String id, Connection connection) throws SQLException;

    List<ItemDTO> getAllItem(Connection connection) throws SQLException;

    String generateNextId(Connection connection) throws SQLException;

    boolean isExist(String id, Connection connection) throws SQLException;
}
