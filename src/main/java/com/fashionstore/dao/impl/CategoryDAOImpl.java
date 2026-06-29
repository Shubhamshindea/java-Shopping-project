package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.model.Category;
import com.fashionstore.util.DBConnection;

public class CategoryDAOImpl implements CategoryDAO {

    private Connection con;

    public CategoryDAOImpl() {
        con = DBConnection.getConnection();
    }

    // ================= SQL =================

    private static final String INSERT_CATEGORY_SQL = """
        INSERT INTO categories (category_name, description)
        VALUES (?, ?)
    """;

    private static final String UPDATE_CATEGORY_SQL = """
        UPDATE categories SET category_name=?, description=?
        WHERE category_id=?
    """;

    private static final String DELETE_CATEGORY_SQL = """
        DELETE FROM categories WHERE category_id=?
    """;

    private static final String GET_BY_ID_SQL = """
        SELECT * FROM categories WHERE category_id=?
    """;

    private static final String GET_BY_NAME_SQL = """
        SELECT * FROM categories WHERE category_name=?
    """;

    private static final String GET_ALL_SQL = """
        SELECT * FROM categories
    """;

    // ================= METHODS =================

    @Override
    public boolean addCategory(Category category) {
        try (PreparedStatement ps = con.prepareStatement(INSERT_CATEGORY_SQL)) {

            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCategory(Category category) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_CATEGORY_SQL)) {

            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getCategoryId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCategory(int categoryId) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_CATEGORY_SQL)) {

            ps.setInt(1, categoryId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Category getCategoryById(int categoryId) {
        try (PreparedStatement ps = con.prepareStatement(GET_BY_ID_SQL)) {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToCategory(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        try (PreparedStatement ps = con.prepareStatement(GET_BY_NAME_SQL)) {

            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToCategory(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() {

        List<Category> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(GET_ALL_SQL)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToCategory(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= HELPER =================

    private Category mapResultSetToCategory(ResultSet rs) throws Exception {

        Category c = new Category();

        c.setCategoryId(rs.getInt("category_id"));
        c.setCategoryName(rs.getString("category_name"));
        c.setDescription(rs.getString("description"));

        return c;
    }
}