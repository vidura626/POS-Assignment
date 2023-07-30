package lk.ijse.Back_end.service.custom;

import lk.ijse.Back_end.dto.ItemDTO;
import lk.ijse.Back_end.dto.OrderDTO;
import lk.ijse.Back_end.service.SuperBO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    void saveOrder(OrderDTO orderDTO, Connection connection) throws SQLException;

    void updateOrder(OrderDTO orderDTO, Connection connection) throws SQLException;

    void deleteOrder(String id, Connection connection) throws SQLException;

    OrderDTO searchOrder(String id, Connection connection) throws SQLException;

    List<OrderDTO> getAllOrders(Connection connection) throws SQLException;

    String generateNextId(Connection connection) throws SQLException;

    boolean isExist(String id, Connection connection) throws SQLException;
}
