package lk.ijse.Back_end.service.custom.imple;

import lk.ijse.Back_end.dao.custom.CustomerDAO;
import lk.ijse.Back_end.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.Back_end.dto.CustomerDTO;
import lk.ijse.Back_end.entity.Customer;
import lk.ijse.Back_end.service.custom.CustomerBO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerBOImple implements CustomerBO {

    private CustomerDAO customerDAO;
    private ModelMapper modelMapper = new ModelMapper();

    public CustomerBOImple() {
        this.customerDAO = new CustomerDAOImpl();
    }

    @Override
    public void saveCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException {
        customerDAO.save(modelMapper.map(customerDTO, Customer.class), connection);
    }


    @Override
    public void updateCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException {
        customerDAO.update(modelMapper.map(customerDTO, Customer.class), connection);
    }

    @Override
    public void deleteCustomer(String id, Connection connection) throws SQLException {
        customerDAO.delete(id, connection);
    }

    @Override
    public CustomerDTO searchCustomer(String id, Connection connection) throws SQLException {
        return modelMapper.map(customerDAO.search(id, connection), CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getAllCustomer(Connection connection) throws SQLException {
        List<Customer> all = customerDAO.getAll(connection);
        List<CustomerDTO> customers = modelMapper.map(all, new TypeToken<List<CustomerDTO>>() {
        }.getType());
        return customers;
    }

    @Override
    public String generateNextId(Connection connection) throws SQLException {
        return customerDAO.generateNextId(connection);
    }

    @Override
    public boolean isExist(String id, Connection connection) throws SQLException {
        return customerDAO.isExist(id, connection);
    }
}
