package lk.ijse.Back_end.service.custom.imple;

import lk.ijse.Back_end.dto.OrderDTO;
import lk.ijse.Back_end.dto.OrderDetailsDTO;
import lk.ijse.Back_end.service.custom.OrderBO;
import lk.ijse.Back_end.service.custom.Order_DetailsBO;
import lombok.SneakyThrows;
import org.glassfish.json.JsonUtil;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

class Order_DetailsBOImpleTest {

    private Order_DetailsBO detailsBO;
    private OrderBO orderBO;
    private Connection connection;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/posjavaee";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "1234";

    @SneakyThrows
    @BeforeEach
    void setUp() throws ClassNotFoundException {
        orderBO = new OrderBOImple();
        detailsBO = new Order_DetailsBOImple();
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        connection.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
    }



    OrderDTO saveOrder(String oId) throws SQLException {
        OrderDTO order = new OrderDTO(oId,"C00-001",new Date(2023,05,25),10,250);
        orderBO.saveOrder(order,connection);
        return order;
    }

    OrderDetailsDTO saveDeatils(String oId,String itemCode) throws SQLException {
        OrderDetailsDTO details = new OrderDetailsDTO(1,oId,itemCode,2,150.00,300.00);
        detailsBO.saveOrderDetails(details,connection);
        return details;
    }

    @SneakyThrows
    @Test
    void saveOrderDetails() {
        assertThrows(Exception.class,()->{
            saveDeatils("P23", "I00-001");
        });
        assertThrows(Exception.class,()->{
            saveDeatils("PO-001", "I00-24ed");
        });
        OrderDTO orderDTO = saveOrder("PO-001");
        saveDeatils("PO-001","I00-001");
        saveDeatils("PO-001","I00-002");
    }

    @SneakyThrows
    @Test
    void updateOrderDetails() {
        OrderDTO orderDTO = saveOrder("PO-001");
        OrderDetailsDTO orderDetailsDTO = saveDeatils(orderDTO.getOrId(), "I00-001");
        List<OrderDetailsDTO> allOrderDetailsByOrderId = detailsBO.getAllOrderDetailsByOrderId(orderDetailsDTO.getOrder_Id(), connection);
        int id = allOrderDetailsByOrderId.get(0).getId();
        System.out.println("Before changes : "+orderDetailsDTO);
        orderDetailsDTO.setId(id);
        orderDetailsDTO.setOrItemPrice(2551053);
        System.out.println("After changes : "+orderDetailsDTO);
        detailsBO.updateOrderDetails(orderDetailsDTO,connection);
        System.out.println("After update : "+allOrderDetailsByOrderId);
        List<OrderDetailsDTO> list = detailsBO.getAllOrderDetailsByOrderId(orderDetailsDTO.getOrder_Id(), connection);
        System.out.println(list);
    }

    @Test
    void deleteOrderDetails() {
    }

    @Test
    void searchOrderDetails() {
    }

    @Test
    void getAllOrderDetails() {
    }

    @Test
    void generateNextId() {
    }

    @Test
    void isExist() {
    }
}