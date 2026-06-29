package com.fashionstore.util;

import java.util.List;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductVariantDAO;

import com.fashionstore.dao.impl.CategoryDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductVariantDAOImpl;

import com.fashionstore.model.Category;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductVariant;

public class DAOTest {

    public static void main(String[] args) {

        // ================= CATEGORY =================
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        List<Category> categories = categoryDAO.getAllCategories();

        System.out.println("===== Categories =====");
        for (Category c : categories) {
            System.out.println(c.getCategoryId() + " - " + c.getCategoryName());
        }

        // ================= PRODUCTS =================
        ProductDAO productDAO = new ProductDAOImpl();
        List<Product> products = productDAO.getAllProducts();

        System.out.println("\n===== Products =====");
        for (Product p : products) {
            System.out.println(p.getProductId() + " - " + p.getProductName() + " - " + p.getPrice());
        }

        // ================= VARIANTS =================
        ProductVariantDAO variantDAO = new ProductVariantDAOImpl();
        List<ProductVariant> variants = variantDAO.getAllVariants();

        System.out.println("\n===== Product Variants =====");
        for (ProductVariant v : variants) {
            System.out.println(
                "VariantID: " + v.getVariantId() +
                " | ProductID: " + v.getProductId() +
                " | Size: " + v.getSize() +
                " | Stock: " + v.getStockQuantity()
            );
        }

        System.out.println("\n===== TEST COMPLETED =====");
    }
}