package com.fashionstore.dao.impl;

import java.sql.*;
import java.util.*;

import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.model.ProductVariant;
import com.fashionstore.util.DBConnection;

public class ProductVariantDAOImpl implements ProductVariantDAO {

    private Connection con;

    public ProductVariantDAOImpl() {
        con = DBConnection.getConnection();
    }

    // ================= SQL =================

    private static final String INSERT_VARIANT_SQL =
            "INSERT INTO product_variants (product_id, size, stock_quantity) VALUES (?, ?, ?)";

    private static final String UPDATE_VARIANT_SQL =
            "UPDATE product_variants SET product_id=?, size=?, stock_quantity=? WHERE variant_id=?";

    private static final String DELETE_VARIANT_SQL =
            "DELETE FROM product_variants WHERE variant_id=?";

    private static final String GET_BY_ID_SQL =
            "SELECT * FROM product_variants WHERE variant_id=?";

    private static final String GET_BY_PRODUCT_SQL =
            "SELECT * FROM product_variants WHERE product_id=?";

    private static final String GET_ALL_SQL =
            "SELECT * FROM product_variants";

    private static final String UPDATE_STOCK_SQL =
            "UPDATE product_variants SET stock_quantity=? WHERE variant_id=?";

    private static final String CHECK_STOCK_SQL =
            "SELECT stock_quantity FROM product_variants WHERE variant_id=?";

    private static final String GET_BY_PRODUCT_AND_SIZE_SQL =
            "SELECT * FROM product_variants WHERE product_id=? AND size=?";

    // ================= METHODS =================

    @Override
    public boolean addProductVariant(ProductVariant variant) {
        try (PreparedStatement ps = con.prepareStatement(INSERT_VARIANT_SQL)) {

            ps.setInt(1, variant.getProductId());
            ps.setString(2, variant.getSize());
            ps.setInt(3, variant.getStockQuantity());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateProductVariant(ProductVariant variant) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_VARIANT_SQL)) {

            ps.setInt(1, variant.getProductId());
            ps.setString(2, variant.getSize());
            ps.setInt(3, variant.getStockQuantity());
            ps.setInt(4, variant.getVariantId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteProductVariant(int variantId) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_VARIANT_SQL)) {

            ps.setInt(1, variantId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ProductVariant getVariantById(int variantId) {
        try (PreparedStatement ps = con.prepareStatement(GET_BY_ID_SQL)) {

            ps.setInt(1, variantId);
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
    public ProductVariant getVariantByProductIdAndSize(int productId, String size) {
        try (PreparedStatement ps = con.prepareStatement(GET_BY_PRODUCT_AND_SIZE_SQL)) {

            ps.setInt(1, productId);
            ps.setString(2, size);

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
    public List<ProductVariant> getVariantsByProductId(int productId) {
        List<ProductVariant> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(GET_BY_PRODUCT_SQL)) {

            ps.setInt(1, productId);
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
    public List<ProductVariant> getAllVariants() {
        List<ProductVariant> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(GET_ALL_SQL)) {

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
    public boolean updateStock(int variantId, int stockQuantity) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_STOCK_SQL)) {

            ps.setInt(1, stockQuantity);
            ps.setInt(2, variantId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isStockAvailable(int variantId, int requiredQuantity) {
        try (PreparedStatement ps = con.prepareStatement(CHECK_STOCK_SQL)) {

            ps.setInt(1, variantId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("stock_quantity") >= requiredQuantity;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= HELPER =================

    private ProductVariant map(ResultSet rs) throws Exception {
        ProductVariant v = new ProductVariant();

        v.setVariantId(rs.getInt("variant_id"));
        v.setProductId(rs.getInt("product_id"));
        v.setSize(rs.getString("size"));
        v.setStockQuantity(rs.getInt("stock_quantity"));

        return v;
    }
}