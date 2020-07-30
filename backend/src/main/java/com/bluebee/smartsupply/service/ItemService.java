package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.dao.ItemDao;
import com.bluebee.smartsupply.model.Items;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService extends CommonService{
    Log logger= LogFactory.getLog(ItemService.class);

    @Autowired
    ItemDao itemDao;

    public List<Items> getAllItems() throws Exception{
        try {
            return  itemDao.getAllItems();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public Items getItemById(int itemid) throws Exception{
        try {
            return  itemDao.getItemById(itemid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public  List<Items> getItemByUserId(int appuserid) throws Exception{
        try {
            return  itemDao.getItemByUserId(appuserid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


    public Items addItem(Items item) throws Exception{
        try {
            return  itemDao.addItem(item);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public Items updateItem(Items item) throws Exception{
        try {
            return  itemDao.updateItem(item);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public Items deleteItem(int itemid) throws Exception{
        try {
            return  itemDao.deleteItem(itemid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


}
