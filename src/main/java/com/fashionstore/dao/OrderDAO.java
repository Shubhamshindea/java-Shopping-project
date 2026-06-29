package com.fashionstore.dao;

import com.fashionstore.model.Order;
import java.util.List;

public interface OrderDAO {

    boolean placeOrder(Order order);

    Order getOrderById(int orderId);

    List<Order> getOrdersByUserId(int userId);

    List<Order> getAllOrders();

    boolean updateOrderStatus(int orderId, String orderStatus);

    boolean deleteOrder(int orderId);

	boolean addOrder(Order order);
}