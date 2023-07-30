package lk.ijse.Back_end.service.custom.imple;


import lk.ijse.Back_end.dao.custom.ItemDAO;
import lk.ijse.Back_end.dao.custom.impl.ItemDAOImpl;
import lk.ijse.Back_end.dto.ItemDTO;
import lk.ijse.Back_end.entity.Item;
import lk.ijse.Back_end.service.custom.ItemBO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ItemBOImple implements ItemBO {
    private ItemDAO itemDAO = new ItemDAOImpl();
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public void saveItem(ItemDTO itemDTO, Connection connection) throws SQLException {
        itemDAO.save(modelMapper.map(itemDTO, Item.class), connection);
    }

    @Override
    public void updateItem(ItemDTO itemDTO, Connection connection) throws SQLException {
        itemDAO.update(modelMapper.map(itemDTO, Item.class), connection);
    }

    @Override
    public void deleteItem(String id, Connection connection) throws SQLException {
        itemDAO.delete(id, connection);
    }

    @Override
    public ItemDTO searchItem(String id, Connection connection) throws SQLException {
        return modelMapper.map(itemDAO.search(id, connection), ItemDTO.class);
    }

    @Override
    public List<ItemDTO> getAllItem(Connection connection) throws SQLException {
        List<Item> all = itemDAO.getAll(connection);
        List<ItemDTO> items = modelMapper.map(all, new TypeToken<List<ItemDTO>>() {
        }.getType());
        return items;
    }

    @Override
    public String generateNextId(Connection connection) throws SQLException {
        return itemDAO.generateNextId(connection);
    }

    @Override
    public boolean isExist(String id, Connection connection) throws SQLException {
        return itemDAO.isExist(id, connection);
    }
}
