package lk.ijse.Back_end.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.Back_end.service.custom.ItemBO;
import lk.ijse.Back_end.service.custom.OrderBO;
import lk.ijse.Back_end.service.custom.imple.ItemBOImple;
import lk.ijse.Back_end.service.custom.imple.OrderBOImple;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "Order Servlet", urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {
    Jsonb jsonb = JsonbBuilder.create();
    OrderBO orderBO = new OrderBOImple();
    @Resource(name = "java:comp/env/jdbc/customer")
    DataSource dataSource;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
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
