package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.model.Cart;
import com.fashionstore.util.DBConnection;

public class CartDAOImpl implements CartDAO {

    private Connection con;

    public CartDAOImpl() {
        con = DBConnection.getConnection();
    }

    // ================= SQL =================

    private static final String INSERT_CART_SQL =
            "INSERT INTO cart (user_id) VALUES (?)";

    private static final String GET_CART_BY_ID_SQL =
            "SELECT * FROM cart WHERE cart_id = ?";

    private static final String GET_CART_BY_USER_SQL =
            "SELECT * FROM cart WHERE user_id = ?";

    private static final String DELETE_CART_SQL =
            "DELETE FROM cart WHERE cart_id = ?";

    // ================= METHODS =================

    @Override
    public boolean createCart(int userId) {

        try (PreparedStatement ps =
                con.prepareStatement(INSERT_CART_SQL)) {

            ps.setInt(1, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Cart getCartById(int cartId) {

        try (PreparedStatement ps =
                con.prepareStatement(GET_CART_BY_ID_SQL)) {

            ps.setInt(1, cartId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapCart(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Cart getCartByUserId(int userId) {

        try (PreparedStatement ps =
                con.prepareStatement(GET_CART_BY_USER_SQL)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapCart(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Cart getOrCreateCartByUserId(int userId) {

        Cart cart = getCartByUserId(userId);

        if (cart != null) {
            return cart;
        }

        boolean created = createCart(userId);

        if (created) {
            return getCartByUserId(userId);
        }

        return null;
    }

    @Override
    public boolean deleteCart(int cartId) {

        try (PreparedStatement ps =
                con.prepareStatement(DELETE_CART_SQL)) {

            ps.setInt(1, cartId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean cartExistsByUserId(int userId) {

        return getCartByUserId(userId) != null;
    }

    // ================= HELPER =================

    private Cart mapCart(ResultSet rs) throws Exception {

        Cart cart = new Cart();

        cart.setCartId(rs.getInt("cart_id"));
        cart.setUserId(rs.getInt("user_id"));

        return cart;
    }
}