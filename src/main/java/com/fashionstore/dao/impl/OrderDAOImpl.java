package com.fashionstore.dao.impl;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.model.Order;
import com.fashionstore.util.DBConnection;

public class OrderDAOImpl implements OrderDAO {

    private Connection con;

    public OrderDAOImpl() {
        con = DBConnection.getConnection();
    }

    // ================= SQL =================

    private static final String INSERT_ORDER_SQL =
        "INSERT INTO orders (user_id, total_amount, payment_method, order_status, " +
        "delivery_name, delivery_phone, delivery_address_line1, delivery_address_line2, " +
        "delivery_city, delivery_state, delivery_pincode, delivery_country) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_ORDER_BY_ID_SQL =
        "SELECT * FROM orders WHERE order_id = ?";

    private static final String GET_ORDERS_BY_USER_ID_SQL =
        "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";

    private static final String GET_ALL_ORDERS_SQL =
        "SELECT * FROM orders ORDER BY created_at DESC";

    private static final String UPDATE_ORDER_STATUS_SQL =
        "UPDATE orders SET order_status = ? WHERE order_id = ?";

    private static final String DELETE_ORDER_SQL =
        "DELETE FROM orders WHERE order_id = ?";

    // ================= METHODS =================

    /**
     * Insert an order and return the generated order_id (0 on failure).
     */
    public int createOrder(Order order) {
        try (PreparedStatement ps = con.prepareStatement(
                INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUserId());
            ps.setBigDecimal(2, order.getTotalAmount());
            ps.setString(3, order.getPaymentMethod());
            ps.setString(4, order.getOrderStatus() != null ? order.getOrderStatus() : "PENDING");
            ps.setString(5, order.getDeliveryName());
            ps.setString(6, order.getDeliveryPhone());
            ps.setString(7, order.getDeliveryAddressLine1());
            ps.setString(8, order.getDeliveryAddressLine2());
            ps.setString(9, order.getDeliveryCity());
            ps.setString(10, order.getDeliveryState());
            ps.setString(11, order.getDeliveryPincode());
            ps.setString(12, order.getDeliveryCountry());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean addOrder(Order order) {
        return createOrder(order) > 0;
    }

    @Override
    public boolean placeOrder(Order order) {
        return addOrder(order);
    }

    @Override
    public Order getOrderById(int orderId) {
        try (PreparedStatement ps = con.prepareStatement(GET_ORDER_BY_ID_SQL)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapOrder(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(GET_ORDERS_BY_USER_ID_SQL)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapOrder(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(GET_ALL_ORDERS_SQL)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapOrder(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateOrderStatus(int orderId, String status) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_ORDER_STATUS_SQL)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteOrder(int orderId) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_ORDER_SQL)) {

            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= HELPER =================

    private Order mapOrder(ResultSet rs) throws Exception {
        Order o = new Order();

        o.setOrderId(rs.getInt("order_id"));
        o.setUserId(rs.getInt("user_id"));
        o.setTotalAmount(rs.getBigDecimal("total_amount"));
        o.setPaymentMethod(rs.getString("payment_method"));
        o.setOrderStatus(rs.getString("order_status"));
        o.setDeliveryName(rs.getString("delivery_name"));
        o.setDeliveryPhone(rs.getString("delivery_phone"));
        o.setDeliveryAddressLine1(rs.getString("delivery_address_line1"));
        o.setDeliveryAddressLine2(rs.getString("delivery_address_line2"));
        o.setDeliveryCity(rs.getString("delivery_city"));
        o.setDeliveryState(rs.getString("delivery_state"));
        o.setDeliveryPincode(rs.getString("delivery_pincode"));
        o.setDeliveryCountry(rs.getString("delivery_country"));

        try {
            o.setCreatedAt(rs.getTimestamp("created_at"));
        } catch (Exception ignored) {}

        return o;
    }
}