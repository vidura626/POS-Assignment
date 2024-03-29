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
import java.util.List;

@WebServlet(name = "Customer Servlet" ,urlPatterns = "/customer")
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
        if (customerBO.isExist(customerDTO.getCustID(), connection)) {
            lk.ijse.Back_end.util.Response response = new Response(400, "Customer already exist", customerDTO.getCustID());
            resp.getWriter().write(jsonb.toJson(response));
            connection.close();
            return;
        }

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
            connection.close();
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

        if (!customerBO.isExist(customerDTO.getCustID(), connection)) {
            lk.ijse.Back_end.util.Response response = new Response(400, "Customer not found", customerDTO.getCustID());
            resp.getWriter().write(jsonb.toJson(response));
            connection.close();
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
            connection.close();
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

        if (!customerBO.isExist(custId, connection)) {
            lk.ijse.Back_end.util.Response response = new Response(400, "Customer not found", custId);
            resp.getWriter().write(jsonb.toJson(response));
            connection.close();
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

        }finally {
            connection.close();
        }
        System.out.println("Customer/Delete method ended");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Customer/Get method called");
        String method = req.getParameter("method");
        switch (method) {
            case "SEARCH":
                searchCustomer(req,resp);
                break;
            case "GETALL":
                getAllCustomers(req,resp);
                break;
            default:
                lk.ijse.Back_end.util.Response response = new Response(400, "Not suitable request", null);
                resp.getWriter().write(jsonb.toJson(response));
        }
        System.out.println("Customer/Get method ended");
    }

    @SneakyThrows
    private void getAllCustomers(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Customer/Get-GetAll method called");
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        Connection connection = dataSource.getConnection();

        List<CustomerDTO> allCustomer = customerBO.getAllCustomer(connection);
        connection.close();
        System.out.println(allCustomer.size());
        Response response = new Response(200, "Success", allCustomer);
        resp.getWriter().write(jsonb.toJson(response));

        System.out.println("Customer/Get-GetAll method ended");
    }

    @SneakyThrows
    private void searchCustomer(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Customer/Get-Search method called");
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        String custId = req.getParameter("custId");

        System.out.println("Customer ID : " + custId);

        Connection connection = dataSource.getConnection();

        if (!customerBO.isExist(custId, connection)) {
            lk.ijse.Back_end.util.Response response = new Response(400, "Customer not found", custId);
            resp.getWriter().write(jsonb.toJson(response));
            connection.close();
            return;
        }
        try {
            CustomerDTO customerDTO = customerBO.searchCustomer(custId, connection);
            lk.ijse.Back_end.util.Response response = new Response(200, "Customer founded", customerDTO);
            resp.getWriter().write(jsonb.toJson(response));
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            lk.ijse.Back_end.util.Response response = new Response(500, "Customer not found", throwables.getLocalizedMessage());
            resp.getWriter().write(jsonb.toJson(response));

        }finally {
            connection.close();
        }
        System.out.println("Customer/Get-Search method ended");
    }
}