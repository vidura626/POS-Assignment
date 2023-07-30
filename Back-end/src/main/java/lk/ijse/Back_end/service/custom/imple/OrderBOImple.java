package lk.ijse.Back_end.service.custom.imple;

import lk.ijse.Back_end.dao.custom.OrderDAO;
import lk.ijse.Back_end.dao.custom.impl.OrderDAOImpl;
import lk.ijse.Back_end.dto.OrderDTO;
import lk.ijse.Back_end.entity.Order;
import lk.ijse.Back_end.service.custom.OrderBO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderBOImple implements OrderBO {

    ModelMapper modelMapper = new ModelMapper();
    private OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    public void saveOrder(OrderDTO orderDTO, Connection connection) throws SQLException {
        orderDAO.save(modelMapper.map(orderDTO, Order.class), connection);
    }

    @Override
    public void updateOrder(OrderDTO orderDTO, Connection connection) throws SQLException {
        orderDAO.update(modelMapper.map(orderDTO, Order.class), connection);
    }

    @Override
    public void deleteOrder(String id, Connection connection) throws SQLException {
        orderDAO.delete(id, connection);
    }

    @Override
    public OrderDTO searchOrder(String id, Connection connection) throws SQLException {
        return modelMapper.map(orderDAO.search(id, connection), OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getAllOrders(Connection connection) throws SQLException {
        List<Order> all = orderDAO.getAll(connection);
        List<OrderDTO> orders = modelMapper.map(all, new TypeToken<List<OrderDTO>>() {
        }.getType());
        return orders;
    }

    @Override
    public String generateNextId(Connection connection) throws SQLException {
        return orderDAO.generateNextId(connection);
    }

    @Override
    public boolean isExist(String id, Connection connection) throws SQLException {
        return orderDAO.isExist(id, connection);
    }
}
