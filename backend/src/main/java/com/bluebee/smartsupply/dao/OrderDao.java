package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.client.GenerateInvoice;
import com.bluebee.smartsupply.client.PushNotificationClient;
import com.bluebee.smartsupply.model.*;
import com.ibm.cloud.objectstorage.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Transactional(rollbackFor = Exception.class)
public class OrderDao extends NamedParameterJdbcDaoSupport {

    static final Order ORDER=new Order();

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private AppUserAddressDao appUserAddressDao;

    @Autowired
    private OrderPaymentDao orderPaymentDao;

    @Autowired
    private OrderDeliveryDao orderDeliveryDao;

    @Autowired
    private OrderStatusDao orderStatusDao;

    @Autowired
    private DeliveryTaskDao deliveryTaskDao;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    PushNotificationClient pushNotificationClient;

    @Autowired
    OrderFileDao orderFileDao;

    @Autowired
    GenerateInvoice generateInvoice;

    public List<Order> getAllOrder(){
    String sql="SELECT  DELIVERY_TASK_ID  taskId,APPUSERCD  driverUserid,OPEN_TASK  openTask,ACCEPT_STAT_CD   acceptstatcd,TOT_ORDER_PRICE totOrderPrice,ORDER_ITM_CNT orderItmcnt,ORDER_STAT_DESC orderStatDesc,CUSTOMERNAME customerName,DELIVERY_ADDRESS deliveryAddress,SHOP_ADDRESS shopAddress,SHOP_NAME shopName, ORDER_BY  orderby ,ORDER_STATUS_CD  status ,ORDER_ID  orderid ,ORDER_TO  orderto ,ORDER_DT  orderdt ,SHOP_CD  shopcd  FROM VSV58378.ORDER_SUMMARY ";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(Order.class));
    }


    public List<Order> getOrderByCustomer(int appusercd){
        Map<String,Object> map = new HashMap<>(1);
        map.put("appusercd",appusercd);
        String sql="SELECT DELIVERY_TYPE deliverytype,PICKUP_TS pickupts,DRIVER_DETAILS driverDetails,TOT_ORDER_PRICE totOrderPrice,ORDER_ITM_CNT orderItmcnt,ORDER_STAT_DESC orderStatDesc,CUSTOMERNAME customerName,DELIVERY_ADDRESS deliveryAddress,SHOP_ADDRESS shopAddress,SHOP_NAME shopName, ORDER_BY  orderby ,ORDER_STATUS_CD  status ,ORDER_ID  orderid ,ORDER_TO  orderto ,ORDER_DT  orderdt ,SHOP_CD  shopcd  FROM VSV58378.ORDER_SUMMARY  WHERE ORDER_BY = :appusercd";
        return namedParameterJdbcTemplate.query(sql,map, new BeanPropertyRowMapper(Order.class));
    }

    public List<Order> getOrderByShopOwner(int appusercd){
        Map<String,Object> map = new HashMap<>(1);
        map.put("appusercd",appusercd);
        String sql="SELECT DELIVERY_TYPE deliverytype,PICKUP_TS pickupts,DRIVER_DETAILS driverDetails, TOT_ORDER_PRICE totOrderPrice,ORDER_ITM_CNT orderItmcnt,ORDER_STAT_DESC orderStatDesc,CUSTOMERNAME customerName,DELIVERY_ADDRESS deliveryAddress,SHOP_ADDRESS shopAddress,SHOP_NAME shopName, taskId,  driverUserid,  openTask,   acceptstatcd,ORDER_BY  orderby ,ORDER_STATUS_CD  status ,ORDER_ID  orderid ,ORDER_TO  orderto ,ORDER_DT  orderdt ,SHOP_CD  shopcd  FROM VSV58378.ORDER_SUMMARY  WHERE ORDER_TO=:appusercd";
        return namedParameterJdbcTemplate.query(sql,map ,new BeanPropertyRowMapper(Order.class));
    }

    public List<Order> getOrderByTransporter(int appusercd){
        Map<String,Object> map = new HashMap<>(1);
        map.put("appusercd",appusercd);
        String sql="SELECT DELIVERY_TYPE deliverytype,PICKUP_TS pickupts,DRIVER_DETAILS driverDetails,TOT_ORDER_PRICE totOrderPrice,ORDER_ITM_CNT orderItmcnt,ORDER_STAT_DESC orderStatDesc,CUSTOMERNAME customerName,DELIVERY_ADDRESS deliveryAddress,SHOP_ADDRESS shopAddress,SHOP_NAME shopName, taskId,  driverUserid,  openTask,   acceptstatcd, ORDER_BY  orderby ,ORDER_STATUS_CD  status ,ORDER_ID  orderid ,ORDER_TO  orderto ,ORDER_DT  orderdt ,SHOP_CD  shopcd  FROM VSV58378.ORDER_SUMMARY  WHERE driverUserid=:appusercd";
        return namedParameterJdbcTemplate.query(sql,map, new BeanPropertyRowMapper(Order.class));
    }

    public Order getOrderSummaryById(int orderid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderid",orderid);
        String sql="SELECT DELIVERY_TYPE deliverytype,PICKUP_TS pickupts,DRIVER_DETAILS driverDetails,TOT_ORDER_PRICE totOrderPrice,ORDER_ITM_CNT orderItmcnt,ORDER_STAT_DESC orderStatDesc,CUSTOMERNAME customerName,DELIVERY_ADDRESS deliveryAddress,SHOP_ADDRESS shopAddress,SHOP_NAME shopName, taskId,  driverUserid,  openTask,   acceptstatcd, ORDER_BY  orderby ,ORDER_STATUS_CD  status ,ORDER_ID  orderid ,ORDER_TO  orderto ,ORDER_DT  orderdt ,SHOP_CD  shopcd  FROM VSV58378.ORDER_SUMMARY  WHERE ORDER_ID = :orderid ";
        return (Order) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(Order.class));
    }

    public Order getOrderById(int orderid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderid",orderid);
        String sql="SELECT  ORDER_BY  orderby ,STATUS  status ,ORDER_ID  orderid ,ORDER_TO  orderto ,ORDER_DT  orderdt ,SHOP_CD  shopcd  FROM VSV58378.ORDER  WHERE ORDER_ID = :orderid ";
        return (Order) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(Order.class));
    }

    public Order addOrder(Order order){
        String sql="INSERT INTO VSV58378.ORDER(ORDER_BY,ORDER_DT,ORDER_ID,ORDER_TO,SHOP_CD,STATUS) values(:orderby,:orderdt,:orderid,:orderto,:shopcd,:status)";
        order.setOrderid(getSequence()+1);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(order);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getOrderById(order.getOrderid());
    }


    private int getSequence(){
        String sql="SELECT max(ORDER_ID) FROM VSV58378.ORDER ";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public Order updateOrder(Order order){
        String sql="UPDATE VSV58378.ORDER set ORDER_BY=:orderby ,STATUS=:status ,ORDER_TO=:orderto ,ORDER_DT=:orderdt ,SHOP_CD=:shopcd  WHERE ORDER_ID = :orderid ";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(order);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (Order) getOrderById(order.getOrderid());
    }

    public Order deleteOrder(int orderid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderid",orderid);
        String sql="DELETE FROM VSV58378.ORDER  WHERE ORDER_ID = :orderid ";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:ORDER;
    }

    public List<Order> processOrderStatus(OrderBundle orderBundle) throws Exception{
        //1-order placed //2- accepted //3-rejected //4-delivery accepted
        //5- delivery rejected // 6 - inTransit //7-delivered //8-cancelled
        AppUser appUser= orderBundle.getAppUser();
        OrderDelivery orderDelivery =  orderBundle.getOrderDelivery();

        OrderStatus orderStatus     =orderBundle.getOrderStatus();
        orderStatus.setUpdateuid(appUser.getAppusercd());
        orderStatus.setOrderts(new Timestamp(System.currentTimeMillis()));
        orderStatusDao.addOrderStatus(orderStatus);

        Order order=  getOrderById(orderStatus.getOrderid());
        order.setStatus(orderStatus.getOrderstatuscd());
        updateOrder(order);

        if(orderStatus.getOrderstatuscd()==2){
            DeliveryTask deliveryTask=  null;
            deliveryTask = deliveryTaskDao.getDeliveryTaskByOrderId(orderStatus.getOrderid());
            if(deliveryTask!=null){
                deliveryTask.setOrderid(orderStatus.getOrderid());
                deliveryTask.setAcceptstatcd(0);
                deliveryTask.setAppusercd(orderDelivery.getDriveruserid());
                deliveryTask.setStatus(1);
                deliveryTaskDao.updateDeliveryTask(deliveryTask);
            }else {
                deliveryTask = new DeliveryTask();
                deliveryTask.setOrderid(orderStatus.getOrderid());
                deliveryTask.setAcceptstatcd(0);
                deliveryTask.setAppusercd(orderDelivery.getDriveruserid());
                deliveryTask.setStatus(1);
                deliveryTaskDao.addDeliveryTask(deliveryTask);
            }

            Order orderprocess = getOrderSummaryById(order.getOrderid());
            String address =   orderprocess.getDeliveryAddress().replaceAll("@@","\n ,");
            String res="{\"appusercd\":\""+appUser.getAppusercd()+"\",\"deliveryaddress\":\""+address+"\"}";
            String base = Base64.getEncoder().encodeToString(res.getBytes());
            pushNotificationClient.pushNotification(base);

        }
        if(orderStatus.getOrderstatuscd()==3){

        }

        if(orderStatus.getOrderstatuscd()==4){
            DeliveryTask deliveryTask=  deliveryTaskDao.getDeliveryTaskByOrderId(orderStatus.getOrderid());
            deliveryTask.setAcceptstatcd(1);
            deliveryTaskDao.updateDeliveryTask(deliveryTask);

         OrderDelivery morDelivery=   orderDeliveryDao.getOrderDeliveryByOrderId(orderStatus.getOrderid());
            morDelivery.setDriveruserid(appUser.getAppusercd());
            orderDeliveryDao.updateOrderDelivery(morDelivery);
        }

        if(orderStatus.getOrderstatuscd()==5){
            DeliveryTask deliveryTask=  deliveryTaskDao.getDeliveryTaskByOrderId(orderStatus.getOrderid());
            deliveryTask.setAcceptstatcd(0);
            deliveryTask.setAppusercd(0);
            deliveryTaskDao.updateDeliveryTask(deliveryTask);

            OrderDelivery morDelivery=   orderDeliveryDao.getOrderDeliveryByOrderId(orderStatus.getOrderid());
            morDelivery.setDriveruserid(0);
            morDelivery.setVechilecd(0);
            orderDeliveryDao.updateOrderDelivery(morDelivery);
        }

        if(orderStatus.getOrderstatuscd()==6){

        }

        if(orderStatus.getOrderstatuscd()==7){

        }

        List returnlst=getOrderByCustomer(appUser.getAppusercd());
        if(appUser.getAppuserrolecd()==2)
            returnlst=getOrderByShopOwner(appUser.getAppusercd());
            if(appUser.getAppuserrolecd()==4)
                returnlst=getOrderByTransporter(appUser.getAppusercd());
        return  returnlst;
    }

    private OrderFile generateInvoice(int orderid) {
        Order order =  getOrderSummaryById(orderid);
        List orderItemList = orderItemDao.getOrderItemByOrderId(orderid);
        Map input=new HashMap();
        input.put("deliveryaddress",order.getDeliveryAddress());
        input.put("shopaddress",order.getShopAddress());
        input.put("orderitems",orderItemList);
        input.put("order",order);
       return generateInvoice.generate(input);
    }

    public S3Object getDownloadObject(int orderid){
       OrderFile orderFile= generateInvoice(orderid);
        return generateInvoice.getDownloadObject(orderid);
    }

    public List<Order> placeOrder(OrderBundle orderBundle) throws Exception{
       List<OrderItem> orderItems = orderBundle.getOrderItems();
       OrderPayment orderPayment= orderBundle.getOrderPayment();
      OrderDelivery orderDelivery =  orderBundle.getOrderDelivery();

       AppUser appUser= orderBundle.getAppUser();
       Map<Integer,List<OrderItem>> orderItemsets= new HashMap<>() ;
       orderItems.stream().forEachOrdered(obj->{
         List temp=  orderItemsets.get(obj.getShopcode());
           if(temp==null)
            temp=new ArrayList<>();
           temp.add(obj);
           orderItemsets.put(obj.getShopcode(),temp);
       });
        orderItemsets.forEach((key,value)->{
          Shop shop =  shopDao.getShopById(key);
            Order order=new Order();
            order.setOrderby(appUser.getAppusercd());
            order.setOrderto(shop.getAppusercd());
            order.setShopcd(key);
            order.setOrderdt(new Timestamp(System.currentTimeMillis()));
            order.setStatus(1);
            addOrder(order);

            value.forEach(val->
            {
                val.setOrderid(order.getOrderid());
                val.setStatus(1);
                orderItemDao.addOrderItem(val);
            });

            orderPayment.setIspaid(0);
            orderPayment.setOrderid(order.getOrderid());
            orderPayment.setStatus(1);
            orderPaymentDao.addOrderPayment(orderPayment);

            if(orderDelivery.getDeliveryaddr()==null || orderDelivery.getDeliveryaddr()==0) {
                try {
                    AppUserAddress appUserAddress = appUserAddressDao.getAddressByUserId(appUser.getAppusercd()).get(0);
                    orderDelivery.setDeliveryaddr(appUserAddress.getAppuseraddrcd());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
           // AppUserAddress appownerAddress = appUserAddressDao.getAddressByUserId(shop.getAppusercd());
            orderDelivery.setFromaddr(shop.getShopcd());

            orderDelivery.setStatus(1);
            orderDelivery.setOrderid(order.getOrderid());
            orderDeliveryDao.addOrderDelivery(orderDelivery);


            OrderStatus orderStatus=new OrderStatus();
            orderStatus.setUpdateuid(appUser.getAppusercd());
            orderStatus.setOrderts(new Timestamp(System.currentTimeMillis()));
            orderStatus.setOrderid(order.getOrderid());
            orderStatus.setOrderstatuscd(1); //1-order placed //2- accepted //3-rejected //4-delivery accepted
            //5- delivery rejected // 6 - inTransit //7-delivered //8-cancelled
            orderStatusDao.addOrderStatus(orderStatus);

            DeliveryTask deliveryTask = new DeliveryTask();
            deliveryTask.setOrderid(orderStatus.getOrderid());
            deliveryTask.setAcceptstatcd(0);
            deliveryTask.setAppusercd(0);
            deliveryTask.setStatus(1);
            deliveryTaskDao.addDeliveryTask(deliveryTask);
        });

   return getOrderByCustomer(appUser.getAppusercd());
    }
}