package com.asuravan.smartsupply.pojo

class OrderBundle {
    lateinit var appUser: AppUser
    lateinit var order:Order;
    lateinit var orderItems:ArrayList<OrderItem>;
    lateinit var orderPayment: OrderPayment;
    lateinit var  orderDelivery: OrderDelivery;
    lateinit var  orderStatus: OrderStatus;
}