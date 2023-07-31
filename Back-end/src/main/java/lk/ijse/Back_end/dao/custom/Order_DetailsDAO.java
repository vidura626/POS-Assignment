package lk.ijse.Back_end.dao.custom;

import lk.ijse.Back_end.dao.CRUDdao;
import lk.ijse.Back_end.entity.OrderDetails;

import java.sql.Connection;
import java.util.List;

public interface Order_DetailsDAO extends CRUDdao<OrderDetails,Integer> {
    List<OrderDetails> getOrderDetailsByOrderId(String orderId, Connection connection);
}
