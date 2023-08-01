package lk.ijse.Back_end.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.Back_end.dto.CustomerDTO;
import lk.ijse.Back_end.dto.ItemDTO;
import lk.ijse.Back_end.dto.OrderDTO;
import lk.ijse.Back_end.dto.OrderDetailsDTO;
import lk.ijse.Back_end.entity.Customer;
import lk.ijse.Back_end.service.custom.CustomerBO;
import lk.ijse.Back_end.service.custom.ItemBO;
import lk.ijse.Back_end.service.custom.OrderBO;
import lk.ijse.Back_end.service.custom.Order_DetailsBO;
import lk.ijse.Back_end.service.custom.imple.CustomerBOImple;
import lk.ijse.Back_end.service.custom.imple.ItemBOImple;
import lk.ijse.Back_end.service.custom.imple.OrderBOImple;
import lk.ijse.Back_end.service.custom.imple.Order_DetailsBOImple;
import lk.ijse.Back_end.util.Response;
import lombok.SneakyThrows;
import org.eclipse.yasson.YassonConfig;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Place Order Servlet", urlPatterns = {"/placeorder"})
public class PlaceOrderServlet extends HttpServlet {
    Jsonb jsonb = JsonbBuilder.create(new YassonConfig().setProperty(YassonConfig.ZERO_TIME_PARSE_DEFAULTING, true));
    @Resource(name = "java:comp/env/jdbc/customer")
    DataSource dataSource;
    Connection connection;

    private OrderBO orderBO = new OrderBOImple();
    private Order_DetailsBO detailsBO = new Order_DetailsBOImple();
    private CustomerBO customerBO = new CustomerBOImple();
    private ItemBO itemBO = new ItemBOImple();


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("PlaceOrder/Get method called");
        String method = req.getParameter("method");
        switch (method) {
            case "SEARCH":
//                searchPlaceOrder(req,resp);
                break;
            case "GETALL":
                getAllOrders(req, resp);
                break;
            default:
                lk.ijse.Back_end.util.Response response = new Response(400, "Not suitable request", null);
                resp.getWriter().write(jsonb.toJson(response));
        }
        System.out.println("PlaceOrder/Get method ended");
    }

    @SneakyThrows
    private void getAllOrders(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        System.out.println("PlaceOrder/Get-GetAll method called");
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        Connection connection = dataSource.getConnection();

        List<OrderDTO> allOrders = orderBO.getAllOrders(connection);
        for (OrderDTO dto : allOrders) {
            dto.setOrCusName(customerBO.searchCustomer(dto.getOrCusId(), connection).getCustName());
        }
        connection.close();
        System.out.println(allOrders.size());
        Response response = new Response(200, "Success", allOrders);
        resp.getWriter().write(jsonb.toJson(response));

        System.out.println("PlaceOrder/Get-GetAll method ended");
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);
//        System.out.println(orderDTO);

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            orderBO.saveOrder(orderDTO, connection);
            for (OrderDetailsDTO detailsDTO : orderDTO.getOrderDetails()) {
                detailsDTO.setOrder_Id(orderDTO.getOrId());
                detailsBO.saveOrderDetails(detailsDTO, connection);
            }
            connection.commit();
            resp.getWriter().write(
                    jsonb.toJson(new Response(200, "Order placed", orderBO.generateNextId(connection)))
            );
        } catch (SQLException throwables) {
            connection.rollback();
            throwables.printStackTrace();
            resp.getWriter().write(
                    jsonb.toJson(new Response(500, "Order placed", throwables.getLocalizedMessage()))
            );
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Put Requested");
        System.out.println("Hiiii");
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("PlaceOrder/Delete method called");
        String orderId = req.getParameter("orderId");
        try {
            connection = dataSource.getConnection();
            orderBO.deleteOrder(orderId, connection);
            resp.getWriter().write(jsonb.toJson(new Response(200, "Order Deleted", orderId)));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.getWriter().write(jsonb.toJson(new Response(200, "Order Delete Failed", throwables.getLocalizedMessage())));
        } finally {
            connection.close();
        }
        System.out.println("PlaceOrder/Delete method ended");
    }
}
