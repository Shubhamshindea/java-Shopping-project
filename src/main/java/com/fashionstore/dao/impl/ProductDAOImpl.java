package com.fashionstore.dao.impl;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

public class ProductDAOImpl implements ProductDAO {
	

    private Connection con;
    
    public ProductDAOImpl() {
        con = DBConnection.getConnection();
    }

    // ================= SQL =================

    private static final String INSERT_PRODUCT_SQL = """
        INSERT INTO products 
        (category_id, product_name, brand, description, price, image_url, is_active)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

    private static final String GET_ALL_PRODUCTS_SQL =
        "SELECT * FROM products";

    private static final String GET_ACTIVE_PRODUCTS_SQL =
        "SELECT * FROM products WHERE is_active = true";

    private static final String GET_PRODUCTS_BY_CATEGORY_SQL =
        "SELECT * FROM products WHERE category_id = ? AND is_active = true";

    private static final String SEARCH_PRODUCTS_SQL =
        "SELECT * FROM products WHERE product_name LIKE ? AND is_active = true";

    private static final String GET_PRODUCTS_BY_PRICE_SQL =
        "SELECT * FROM products WHERE price BETWEEN ? AND ? AND is_active = true";

    private static final String GET_PRODUCT_BY_ID_SQL =
        "SELECT * FROM products WHERE product_id = ?";

    private static final String UPDATE_PRODUCT_SQL =
        "UPDATE products SET category_id=?, product_name=?, brand=?, description=?, price=?, image_url=?, is_active=? WHERE product_id=?";

    private static final String DELETE_PRODUCT_SQL =
        "DELETE FROM products WHERE product_id=?";

    // ================= BASIC METHODS =================

    @Override
    public boolean addProduct(Product product) {
        try (PreparedStatement ps = con.prepareStatement(INSERT_PRODUCT_SQL)) {

            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getBrand());
            ps.setString(4, product.getDescription());
            ps.setBigDecimal(5, product.getPrice());
            ps.setString(6, product.getImageUrl());
            ps.setBoolean(7, product.isActive());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(GET_ALL_PRODUCTS_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Product> getAllActiveProducts() {
        List<Product> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(GET_ACTIVE_PRODUCTS_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(GET_PRODUCTS_BY_CATEGORY_SQL)) {

            ps.setInt(1, categoryId);
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
    public List<Product> searchProducts(String keyword) {
        List<Product> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(SEARCH_PRODUCTS_SQL)) {

            ps.setString(1, "%" + keyword + "%");
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
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(GET_PRODUCTS_BY_PRICE_SQL)) {

            ps.setBigDecimal(1, minPrice);
            ps.setBigDecimal(2, maxPrice);

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
    public Product getProductById(int productId) {

        System.out.println("🔥 DAO METHOD CALLED");
        System.out.println("Product ID: " + productId);

        try (PreparedStatement ps = con.prepareStatement(GET_PRODUCT_BY_ID_SQL)) {

            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("✅ PRODUCT FOUND IN DB");
                return map(rs);
            } else {
                System.out.println("❌ NO PRODUCT FOUND IN DB");
            }

        } catch (Exception e) {
            System.out.println("❌ ERROR IN DAO:");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean updateProduct(Product product) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_PRODUCT_SQL)) {

            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getBrand());
            ps.setString(4, product.getDescription());
            ps.setBigDecimal(5, product.getPrice());
            ps.setString(6, product.getImageUrl());
            ps.setBoolean(7, product.isActive());
            ps.setInt(8, product.getProductId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteProduct(int productId) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_PRODUCT_SQL)) {

            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= SORT =================

    @Override
    public List<Product> getProductsSortedByPriceAsc() {
        return getSorted("ASC");
    }

    @Override
    public List<Product> getProductsSortedByPriceDesc() {
        return getSorted("DESC");
    }

    private List<Product> getSorted(String order) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY price " + order;

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= LATEST =================

    @Override
    public List<Product> getLatestProducts(int limit) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY created_at DESC LIMIT ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= RELATED =================

    @Override
    public List<Product> getRelatedProducts(int categoryId, int excludeProductId, int limit) {
        List<Product> list = new ArrayList<>();

        String sql = """
            SELECT * FROM products 
            WHERE category_id = ? AND product_id != ?
            LIMIT ?
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ps.setInt(2, excludeProductId);
            ps.setInt(3, limit);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= FILTER =================

    @Override
    public List<Product> getFilteredProducts(Integer categoryId, String keyword,
            BigDecimal minPrice, BigDecimal maxPrice, String sort) {

        List<Product> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE is_active = true");

        if (categoryId != null) sql.append(" AND category_id=?");
        if (keyword != null && !keyword.isEmpty()) sql.append(" AND product_name LIKE ?");
        if (minPrice != null && maxPrice != null) sql.append(" AND price BETWEEN ? AND ?");

        if ("asc".equalsIgnoreCase(sort)) sql.append(" ORDER BY price ASC");
        if ("desc".equalsIgnoreCase(sort)) sql.append(" ORDER BY price DESC");

        try (PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int i = 1;

            if (categoryId != null) ps.setInt(i++, categoryId);
            if (keyword != null && !keyword.isEmpty()) ps.setString(i++, "%" + keyword + "%");
            if (minPrice != null && maxPrice != null) {
                ps.setBigDecimal(i++, minPrice);
                ps.setBigDecimal(i++, maxPrice);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= MAPPER =================

    private Product map(ResultSet rs) throws Exception {
        Product p = new Product();

        p.setProductId(rs.getInt("product_id"));
        p.setCategoryId(rs.getInt("category_id"));
        p.setProductName(rs.getString("product_name"));
        p.setBrand(rs.getString("brand"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setImageUrl(rs.getString("image_url"));
        p.setActive(rs.getBoolean("is_active"));

        return p;
    }
}