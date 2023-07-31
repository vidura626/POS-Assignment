package lk.ijse.Back_end.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.Back_end.dto.ItemDTO;
import lk.ijse.Back_end.service.custom.CustomerBO;
import lk.ijse.Back_end.service.custom.ItemBO;
import lk.ijse.Back_end.service.custom.imple.CustomerBOImple;
import lk.ijse.Back_end.service.custom.imple.ItemBOImple;
import lk.ijse.Back_end.util.Response;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

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

@WebServlet(name = "Item  Servlet",urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    Jsonb jsonb = JsonbBuilder.create();

    ItemBO itemBO = new ItemBOImple();

    @Resource(name = "java:comp/env/jdbc/customer")
    DataSource dataSource;

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Item/Post method called");
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);

        System.out.println(itemDTO);


        Connection connection = dataSource.getConnection();
        if (itemBO.isExist(itemDTO.getItemCode(), connection)) {
            lk.ijse.Back_end.util.Response response = new Response(400, "Item already exist", itemDTO.getItemCode());
            resp.getWriter().write(jsonb.toJson(response));
            connection.close();
            return;
        }

        itemDTO.setItemCode(itemBO.generateNextId(connection));

        connection.setAutoCommit(false);
        try {
            itemBO.saveItem(itemDTO, connection);
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

        System.out.println("Item/Post method ended");
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Item/Put method called");
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);

        System.out.println(itemDTO);

        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);

        if (!itemBO.isExist(itemDTO.getItemCode(), connection)) {
            lk.ijse.Back_end.util.Response response = new Response(400, "Item not found", itemDTO.getItemCode());
            resp.getWriter().write(jsonb.toJson(response));
            connection.close();
            return;
        }

        try {
            itemBO.updateItem(itemDTO, connection);
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
        System.out.println("Item/Delete method called");
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        String itemCode = req.getParameter("itemCode");

        System.out.println("itemCode : " + itemCode);

        Connection connection = dataSource.getConnection();

        if (!itemBO.isExist(itemCode, connection)) {
            lk.ijse.Back_end.util.Response response = new Response(400, "Item not found", itemCode);
            resp.getWriter().write(jsonb.toJson(response));
            connection.close();
            return;
        }
        try {
            itemBO.deleteItem(itemCode, connection);
            lk.ijse.Back_end.util.Response response = new Response(200, "Delete Successfully : ", itemCode);
            resp.getWriter().write(jsonb.toJson(response));
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            lk.ijse.Back_end.util.Response response = new Response(500, "Delete Failed : ", throwables.getLocalizedMessage());
            resp.getWriter().write(jsonb.toJson(response));

        }finally {
            connection.close();
        }
        System.out.println("Item/Delete method ended");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Item/Get method called");
        String method = req.getParameter("method");
        switch (method) {
            case "SEARCH":
                searchItem(req,resp);
                break;
            case "GETALL":
                getAllItems(req,resp);
                break;
            default:
                lk.ijse.Back_end.util.Response response = new Response(400, "Not suitable request", null);
                resp.getWriter().write(jsonb.toJson(response));
        }
        System.out.println("Item/Get method ended");
    }

    @SneakyThrows
    private void getAllItems(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Item/Get-GetAll method called");
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        Connection connection = dataSource.getConnection();

        List<ItemDTO> allItem = itemBO.getAllItem(connection);
        connection.close();
        System.out.println(allItem.size());
        Response response = new Response(200, "Success", allItem);
        resp.getWriter().write(jsonb.toJson(response));

        System.out.println("Customer/Get-GetAll method ended");
    }

    @SneakyThrows
    private void searchItem(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Item/Get-Search method called");
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        String itemCode = req.getParameter("itemCode");

        System.out.println("Item Code : " + itemCode);

        Connection connection = dataSource.getConnection();

        if (!itemBO.isExist(itemCode, connection)) {
            lk.ijse.Back_end.util.Response response = new Response(400, "Item not found", itemCode);
            resp.getWriter().write(jsonb.toJson(response));
            connection.close();
            return;
        }
        try {
            ItemDTO itemDTO = itemBO.searchItem(itemCode, connection);
            lk.ijse.Back_end.util.Response response = new Response(200, "Item founded", itemDTO);
            resp.getWriter().write(jsonb.toJson(response));
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            lk.ijse.Back_end.util.Response response = new Response(500, "Item not found", throwables.getLocalizedMessage());
            resp.getWriter().write(jsonb.toJson(response));

        }finally {
            connection.close();
        }
        System.out.println("Item/Get-Search method ended");
    }
}
