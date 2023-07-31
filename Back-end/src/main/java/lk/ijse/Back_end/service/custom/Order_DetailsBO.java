package lk.ijse.Back_end.service.custom;

import lk.ijse.Back_end.dto.OrderDetailsDTO;
import lk.ijse.Back_end.service.SuperBO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Order_DetailsBO extends SuperBO {
    void saveOrderDetails(OrderDetailsDTO orderDetailsDTO, Connection connection) throws SQLException;

    void updateOrderDetails(OrderDetailsDTO orderDetailsDTO, Connection connection) throws SQLException;

    void deleteOrderDetails(int id, Connection connection) throws SQLException;

    OrderDetailsDTO searchOrderDetails(int id, Connection connection) throws SQLException;

    List<OrderDetailsDTO> getAllOrderDetails(Connection connection) throws SQLException;

    List<OrderDetailsDTO> getAllOrderDetailsByOrderId(String oId,Connection connection) throws SQLException;

    String generateNextId(Connection connection) throws SQLException;

    boolean isExist(int id, Connection connection) throws SQLException;
}
