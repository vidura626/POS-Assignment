package lk.ijse.Back_end.service.custom.imple;

import lk.ijse.Back_end.dao.custom.OrderDAO;
import lk.ijse.Back_end.dao.custom.impl.OrderDAOImpl;
import lk.ijse.Back_end.entity.Order;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderBOImpleTest {
    private OrderDAO orderDAO;
    private Connection connection;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/posjavaee";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "1234";

    @SneakyThrows
    @BeforeEach
    void setUp() throws ClassNotFoundException {
        orderDAO = new OrderDAOImpl();
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        connection.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
    }

    @SneakyThrows
    @Test
    void saveOrder() {
        Order order = saveOrderTest();
        Order search = orderDAO.search(order.getOrId(), connection);
        assertNotNull(search);
        System.out.println(search);
    }

    Order saveOrderTest() throws SQLException {
        Order order = new Order("PO-001","C00-001",new Date(2023,05,25),10,250);
        orderDAO.save(order,connection);
        return order;
    }

    @SneakyThrows
    @Test
    void updateOrder() {
        Order order = saveOrderTest();
        order.setOrDis(50);
        orderDAO.update(order,connection);
        Order search = orderDAO.search(order.getOrId(), connection);
        assertNotNull(search);
        System.out.println(search);
    }

    @SneakyThrows
    @Test
    void deleteOrder() {
        Order order = saveOrderTest();
        orderDAO.delete(order.getOrId(),connection);
        Order search = orderDAO.search(order.getOrId(), connection);
        assertNull(search);
    }

    @SneakyThrows
    @Test
    void searchOrder() {
        Order o001 = orderDAO.search("PO-001", connection);
        assertNull(o001);
        saveOrderTest();
        Order o002 = orderDAO.search("PO-O01", connection);
        assertNotNull(o002);
    }

    @SneakyThrows
    @Test
    void getAllOrders() {

        assertDoesNotThrow(()-> {
            List<Order> all = orderDAO.getAll(connection);
        });
        saveOrderTest();
        List<Order> all = orderDAO.getAll(connection);
        assertEquals(1,all.size());
    }

    @Test
    void generateNextId() throws SQLException {
        String id1 = orderDAO.generateNextId(connection);
        assertEquals("PO-001",id1);
        saveOrderTest();
        String id2 = orderDAO.generateNextId(connection);
        assertEquals("PO-002",id2);

    }

    @SneakyThrows
    @Test
    void isExist() {
        boolean exist = orderDAO.isExist("PO-001", connection);
        assertFalse(exist);
        saveOrderTest();
        boolean exist2 = orderDAO.isExist("PO-001", connection);
        assertTrue(exist2);
    }
}