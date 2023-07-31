package lk.ijse.Back_end.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.Back_end.dto.OrderDTO;
import lk.ijse.Back_end.dto.OrderDetailsDTO;
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


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
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
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
