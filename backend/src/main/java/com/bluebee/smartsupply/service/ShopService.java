package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.dao.ShopDao;
import com.bluebee.smartsupply.model.Shop;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService extends CommonService{
    Log logger= LogFactory.getLog(ShopService.class);

    @Autowired
    ShopDao shopDao;

    public List<Shop> getAllShop() throws Exception{
        try {
            return  shopDao.getAllShop();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


    public List<Shop> getShopByCriteria(Shop shop) throws Exception{
        try {
            return  shopDao.getShopByCriteria( shop);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


    public Shop getShopById(int shop_cd) throws Exception{
        try {
            return  shopDao.getShopById(shop_cd);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public List<Shop> getShopByUserId(int appusercd) throws Exception{
        try {
            return  shopDao.getShopByUserId(appusercd);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }



    public Shop addShop(Shop shop) throws Exception{
        try {
            return  shopDao.addShop(shop);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public Shop updateShop(Shop shop) throws Exception{
        try {
            return  shopDao.updateShop(shop);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public Shop deleteShop(int shop_cd) throws Exception{
        try {
            return  shopDao.deleteShop(shop_cd);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


}