package lk.ijse.Back_end.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.Back_end.dto.CustomerDTO;
import lk.ijse.Back_end.service.custom.CustomerBO;
import lk.ijse.Back_end.service.custom.imple.CustomerBOImple;
import lk.ijse.Back_end.util.Response;
import lombok.SneakyThrows;

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

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    Jsonb jsonb = JsonbBuilder.create();

    CustomerBO customerBO = new CustomerBOImple();

    @Resource(name = "java:comp/env/jdbc/customer")
    DataSource dataSource;

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Customer/Post method called");
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

        System.out.println(customerDTO);

        Connection connection = dataSource.getConnection();
        customerDTO.setCustID(customerBO.generateNextId(connection));

        connection.setAutoCommit(false);
        try {
            customerBO.saveCustomer(customerDTO, connection);
            connection.commit();

            lk.ijse.Back_end.util.Response response = new Response(200, "Saved Successfully", null);
            resp.getWriter().write(jsonb.toJson(response));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();

            lk.ijse.Back_end.util.Response response = new Response(500, "Saved Failed : ", throwables.getLocalizedMessage());
            resp.getWriter().write(jsonb.toJson(response));

        } finally {
            connection.setAutoCommit(true);
        }

        System.out.println("Customer/Post method ended");
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Customer/Put method called");
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

        System.out.println(customerDTO);

        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);

        if(!customerBO.isExist(customerDTO.getCustID(),connection)){
            lk.ijse.Back_end.util.Response response = new Response(400, "Customer not found", customerDTO.getCustID());
            resp.getWriter().write(jsonb.toJson(response));
            return;
        }

        try {
            customerBO.updateCustomer(customerDTO, connection);
            connection.commit();

            lk.ijse.Back_end.util.Response response = new Response(200, "Update Successfully", null);
            resp.getWriter().write(jsonb.toJson(response));
        } catch (SQLException throwables) {

            throwables.printStackTrace();
            connection.rollback();

            lk.ijse.Back_end.util.Response response = new Response(500, "Update Failed : ", throwables.getLocalizedMessage());
            resp.getWriter().write(jsonb.toJson(response));

        } finally {
            connection.setAutoCommit(true);
        }

        System.out.println("Customer/Put method ended");
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Customer/Delete method called");
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        String custId = req.getParameter("custId");

        System.out.println("Customer ID : " + custId);

        Connection connection = dataSource.getConnection();

        if(!customerBO.isExist(custId,connection)){
            lk.ijse.Back_end.util.Response response = new Response(400, "Customer not found", custId);
            resp.getWriter().write(jsonb.toJson(response));
            return;
        }
        try {
            customerBO.deleteCustomer(custId, connection);
            lk.ijse.Back_end.util.Response response = new Response(200, "Delete Successfully : ", custId);
            resp.getWriter().write(jsonb.toJson(response));
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            lk.ijse.Back_end.util.Response response = new Response(500, "Delete Failed : ", throwables.getLocalizedMessage());
            resp.getWriter().write(jsonb.toJson(response));

        }
        System.out.println("Customer/Delete method ended");
    }
}