package com.fashionstore.dao.impl;

import java.sql.*;
import java.util.*;

import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

public class CartItemDAOImpl implements CartItemDAO {

    private Connection con;

    public CartItemDAOImpl() {
        con = DBConnection.getConnection();
    }

    // ================= SQL =================

    private static final String ADD_CART_ITEM_SQL =
            "INSERT INTO cart_items (cart_id, product_id, variant_id, quantity) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_CART_ITEM_SQL =
            "UPDATE cart_items SET quantity = ? WHERE cart_item_id = ?";

    private static final String DELETE_CART_ITEM_SQL =
            "DELETE FROM cart_items WHERE cart_item_id = ?";

    private static final String GET_CART_ITEMS_SQL =
            "SELECT * FROM cart_items WHERE cart_id = ?";

    private static final String CLEAR_CART_SQL =
            "DELETE FROM cart_items WHERE cart_id = ?";

    private static final String GET_ITEM_BY_ID_SQL =
            "SELECT * FROM cart_items WHERE cart_item_id = ?";

    private static final String GET_ITEM_BY_CART_AND_VARIANT_SQL =
            "SELECT * FROM cart_items WHERE cart_id = ? AND variant_id = ?";

    private static final String UPDATE_QTY_BY_CART_VARIANT_SQL =
            "UPDATE cart_items SET quantity = ? WHERE cart_id = ? AND variant_id = ?";

    private static final String REMOVE_BY_CART_VARIANT_SQL =
            "DELETE FROM cart_items WHERE cart_id = ? AND variant_id = ?";

    private static final String COUNT_ITEMS_SQL =
            "SELECT COUNT(*) FROM cart_items WHERE cart_id = ?";

    // ================= METHODS =================

    @Override
    public boolean addCartItem(CartItem item) {
        try (PreparedStatement ps = con.prepareStatement(ADD_CART_ITEM_SQL)) {

            ps.setInt(1, item.getCartId());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getVariantId());
            ps.setInt(4, item.getQuantity());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCartItem(int cartItemId, int quantity) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_CART_ITEM_SQL)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeCartItem(int cartItemId) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_CART_ITEM_SQL)) {

            ps.setInt(1, cartItemId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<CartItem> getCartItems(int cartId) {
        List<CartItem> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(GET_CART_ITEMS_SQL)) {

            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapCartItem(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean clearCart(int cartId) {
        try (PreparedStatement ps = con.prepareStatement(CLEAR_CART_SQL)) {

            ps.setInt(1, cartId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCartItemQuantity(int cartItemId, int quantity) {
        return updateCartItem(cartItemId, quantity);
    }

    @Override
    public boolean updateCartItemQuantityByCartAndVariant(int cartId, int variantId, int quantity) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_QTY_BY_CART_VARIANT_SQL)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartId);
            ps.setInt(3, variantId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeCartItemByCartAndVariant(int cartId, int variantId) {
        try (PreparedStatement ps = con.prepareStatement(REMOVE_BY_CART_VARIANT_SQL)) {

            ps.setInt(1, cartId);
            ps.setInt(2, variantId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public CartItem getCartItemById(int cartItemId) {
        try (PreparedStatement ps = con.prepareStatement(GET_ITEM_BY_ID_SQL)) {

            ps.setInt(1, cartItemId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapCartItem(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CartItem getCartItemByCartIdAndVariantId(int cartId, int variantId) {
        try (PreparedStatement ps = con.prepareStatement(GET_ITEM_BY_CART_AND_VARIANT_SQL)) {

            ps.setInt(1, cartId);
            ps.setInt(2, variantId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapCartItem(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {
        return getCartItems(cartId);
    }

    @Override
    public int getCartItemCount(int cartId) {
        try (PreparedStatement ps = con.prepareStatement(COUNT_ITEMS_SQL)) {

            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ================= HELPER =================

    private CartItem mapCartItem(ResultSet rs) throws Exception {
        CartItem item = new CartItem();

        item.setCartItemId(rs.getInt("cart_item_id"));
        item.setCartId(rs.getInt("cart_id"));
        item.setProductId(rs.getInt("product_id"));
        item.setVariantId(rs.getInt("variant_id"));
        item.setQuantity(rs.getInt("quantity"));

        return item;
    }
}