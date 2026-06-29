package com.fashionstore.dao;

import java.math.BigDecimal;
import java.util.List;
import com.fashionstore.model.Product;

public interface ProductDAO {

    // CRUD
    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(int productId);

    Product getProductById(int productId);
    

    // BASIC FETCH
    List<Product> getAllProducts();

    List<Product> getAllActiveProducts();

    List<Product> getProductsByCategory(int categoryId);

    // SEARCH
    List<Product> searchProducts(String keyword);

    // FILTERS
    List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> getProductsSortedByPriceAsc();

    List<Product> getProductsSortedByPriceDesc();

    List<Product> getLatestProducts(int limit);

    // ADVANCED FILTER (VERY IMPORTANT)
    List<Product> getFilteredProducts(Integer categoryId,
                                      String keyword,
                                      BigDecimal minPrice,
                                      BigDecimal maxPrice,
                                      String sort);

    // RELATED PRODUCTS
    List<Product> getRelatedProducts(int categoryId,
                                    int excludeProductId,
                                    int limit);
}