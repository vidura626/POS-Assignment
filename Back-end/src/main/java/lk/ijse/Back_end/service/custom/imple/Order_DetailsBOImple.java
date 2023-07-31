package lk.ijse.Back_end.service.custom.imple;

import lk.ijse.Back_end.dao.custom.Order_DetailsDAO;
import lk.ijse.Back_end.dao.custom.impl.Order_DetailsDAOImple;
import lk.ijse.Back_end.dto.OrderDTO;
import lk.ijse.Back_end.dto.OrderDetailsDTO;
import lk.ijse.Back_end.entity.Order;
import lk.ijse.Back_end.entity.OrderDetails;
import lk.ijse.Back_end.service.custom.Order_DetailsBO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Order_DetailsBOImple implements Order_DetailsBO {

    Order_DetailsDAO order_detailsDAO = new Order_DetailsDAOImple();
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public void saveOrderDetails(OrderDetailsDTO orderDetailsDTO, Connection connection) throws SQLException {
        order_detailsDAO.save(modelMapper.map(orderDetailsDTO, OrderDetails.class), connection);
    }

    @Override
    public void updateOrderDetails(OrderDetailsDTO orderDetailsDTO, Connection connection) throws SQLException {
        order_detailsDAO.update(modelMapper.map(orderDetailsDTO, OrderDetails.class), connection);
    }

    @Override
    public void deleteOrderDetails(int id, Connection connection) throws SQLException {
        order_detailsDAO.delete(id, connection);
    }

    @Override
    public OrderDetailsDTO searchOrderDetails(int id, Connection connection) throws SQLException {
        return modelMapper.map(order_detailsDAO.search(id, connection), OrderDetailsDTO.class);
    }

    @Override
    public List<OrderDetailsDTO> getAllOrderDetails(Connection connection) throws SQLException {
        List<OrderDetails> all = order_detailsDAO.getAll(connection);
        List<OrderDetailsDTO> orderDetails = modelMapper.map(all, new TypeToken<List<OrderDetailsDTO>>() {
        }.getType());
        return orderDetails;
    }

    @Override
    public List<OrderDetailsDTO> getAllOrderDetailsByOrderId(String oId, Connection connection) throws SQLException {
        List<OrderDetails> all = order_detailsDAO.getOrderDetailsByOrderId(oId,connection);
        List<OrderDetailsDTO> orderDetails = modelMapper.map(all, new TypeToken<List<OrderDetailsDTO>>() {
        }.getType());
        return orderDetails;
    }

    @Override
    public String generateNextId(Connection connection) throws SQLException {
        return order_detailsDAO.generateNextId(connection);
    }

    @Override
    public boolean isExist(int id, Connection connection) throws SQLException {
        return order_detailsDAO.isExist(id, connection);
    }
}
