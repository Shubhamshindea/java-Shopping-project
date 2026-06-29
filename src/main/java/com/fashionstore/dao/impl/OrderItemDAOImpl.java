package com.fashionstore.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.model.OrderItem;
import com.fashionstore.util.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    private Connection con;

    public OrderItemDAOImpl() {
        con = DBConnection.getConnection();
    }

    // ================= SQL =================

    private static final String INSERT_SQL =
        "INSERT INTO order_items (order_id, variant_id, quantity, price) VALUES (?, ?, ?, ?)";

    private static final String GET_BY_ID_SQL =
        "SELECT * FROM order_items WHERE order_item_id = ?";

    private static final String GET_BY_ORDER_SQL =
        "SELECT * FROM order_items WHERE order_id = ?";

    private static final String DELETE_SQL =
        "DELETE FROM order_items WHERE order_item_id = ?";

    private static final String DELETE_BY_ORDER_SQL =
        "DELETE FROM order_items WHERE order_id = ?";

    // ================= METHODS =================

    @Override
    public boolean addOrderItem(OrderItem item) {
        try (PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getVariantId());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getPrice());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ IMPORTANT (List insert)
    @Override
    public boolean addOrderItems(List<OrderItem> items) {
        try (PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            for (OrderItem item : items) {
                ps.setInt(1, item.getOrderId());
                ps.setInt(2, item.getVariantId());
                ps.setInt(3, item.getQuantity());
                ps.setBigDecimal(4, item.getPrice());
                ps.addBatch();
            }

            ps.executeBatch();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public OrderItem getOrderItemById(int id) {
        try (PreparedStatement ps = con.prepareStatement(GET_BY_ID_SQL)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        return getItemsByOrderId(orderId); // reuse
    }

    @Override
    public List<OrderItem> getItemsByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(GET_BY_ORDER_SQL)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteOrderItem(int id) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteOrderItemsByOrderId(int orderId) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_BY_ORDER_SQL)) {

            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= HELPER =================

    private OrderItem map(ResultSet rs) throws Exception {
        OrderItem item = new OrderItem();

        item.setOrderItemId(rs.getInt("order_item_id"));
        item.setOrderId(rs.getInt("order_id"));
        item.setVariantId(rs.getInt("variant_id"));
        item.setQuantity(rs.getInt("quantity"));
        item.setPrice(rs.getBigDecimal("price"));

        return item;
    }
}