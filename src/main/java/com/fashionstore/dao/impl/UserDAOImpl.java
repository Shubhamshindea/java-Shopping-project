package com.fashionstore.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.model.User;
import com.fashionstore.util.DBConnection;

public class UserDAOImpl implements UserDAO {

    private Connection con;

    public UserDAOImpl() {
        con = DBConnection.getConnection();
    }

    // ================= SQL CONSTANTS =================

    private static final String INSERT_USER_SQL = """
        INSERT INTO users
        (full_name, email, phone, password, address_line1, address_line2, city, state, pincode, country)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

    private static final String LOGIN_USER_SQL = """
        SELECT * FROM users
        WHERE email = ? AND password = ?
    """;

    private static final String GET_USER_BY_ID_SQL = """
        SELECT * FROM users WHERE user_id = ?
    """;

    private static final String GET_USER_BY_EMAIL_SQL = """
        SELECT * FROM users WHERE email = ?
    """;

    private static final String GET_USER_BY_PHONE_SQL = """
        SELECT * FROM users WHERE phone = ?
    """;

    private static final String UPDATE_USER_SQL = """
        UPDATE users SET
        full_name=?, email=?, phone=?, password=?, address_line1=?, address_line2=?, city=?, state=?, pincode=?, country=?
        WHERE user_id=?
    """;

    private static final String UPDATE_PASSWORD_SQL = """
        UPDATE users SET password=? WHERE user_id=?
    """;

    private static final String GET_ALL_USERS_SQL = """
        SELECT * FROM users
    """;

    private static final String CHECK_EMAIL_SQL = """
        SELECT * FROM users WHERE email=?
    """;

    private static final String CHECK_PHONE_SQL = """
        SELECT * FROM users WHERE phone=?
    """;

    // ================= METHODS =================

    @Override
    public boolean registerUser(User user) {
        try (PreparedStatement ps = con.prepareStatement(INSERT_USER_SQL)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getAddressLine1());
            ps.setString(6, user.getAddressLine2());
            ps.setString(7, user.getCity());
            ps.setString(8, user.getState());
            ps.setString(9, user.getPincode());
            ps.setString(10, user.getCountry());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User loginUser(String email, String password) {
        try (PreparedStatement ps = con.prepareStatement(LOGIN_USER_SQL)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserById(int userId) {
        try (PreparedStatement ps = con.prepareStatement(GET_USER_BY_ID_SQL)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        try (PreparedStatement ps = con.prepareStatement(GET_USER_BY_EMAIL_SQL)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByPhone(String phone) {
        try (PreparedStatement ps = con.prepareStatement(GET_USER_BY_PHONE_SQL)) {

            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_USER_SQL)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getAddressLine1());
            ps.setString(6, user.getAddressLine2());
            ps.setString(7, user.getCity());
            ps.setString(8, user.getState());
            ps.setString(9, user.getPincode());
            ps.setString(10, user.getCountry());
            ps.setInt(11, user.getUserId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePassword(int userId, String newPassword) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_PASSWORD_SQL)) {

            ps.setString(1, newPassword);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean emailExists(String email) {
        try (PreparedStatement ps = con.prepareStatement(CHECK_EMAIL_SQL)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean phoneExists(String phone) {
        try (PreparedStatement ps = con.prepareStatement(CHECK_PHONE_SQL)) {

            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(GET_ALL_USERS_SQL)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    // ================= HELPER =================

    private User mapResultSetToUser(ResultSet rs) throws Exception {

        User user = new User();

        user.setUserId(rs.getInt("user_id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setPassword(rs.getString("password"));
        user.setAddressLine1(rs.getString("address_line1"));
        user.setAddressLine2(rs.getString("address_line2"));
        user.setCity(rs.getString("city"));
        user.setState(rs.getString("state"));
        user.setPincode(rs.getString("pincode"));
        user.setCountry(rs.getString("country"));
        user.setCreatedAt(rs.getTimestamp("created_at"));

        return user;
    }
}