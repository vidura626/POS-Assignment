package lk.ijse.Back_end.service.custom;

import lk.ijse.Back_end.dto.CustomerDTO;
import lk.ijse.Back_end.service.SuperBO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    void saveCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException;

    void updateCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException;

    void deleteCustomer(String id, Connection connection) throws SQLException;

    CustomerDTO searchCustomer(String id, Connection connection) throws SQLException;

    List<CustomerDTO> getAllCustomer(Connection connection);

    String generateNextId(Connection connection) throws SQLException;

    boolean isExist(String id, Connection connection) throws SQLException;
}
