package com.fashionstore.dao;

import com.fashionstore.model.ProductVariant;
import java.util.List;

public interface ProductVariantDAO {

    boolean addProductVariant(ProductVariant productVariant);

    boolean updateProductVariant(ProductVariant productVariant);

    boolean deleteProductVariant(int variantId);

    ProductVariant getVariantById(int variantId);

    ProductVariant getVariantByProductIdAndSize(int productId, String size);

    List<ProductVariant> getVariantsByProductId(int productId);

    List<ProductVariant> getAllVariants();

    boolean updateStock(int variantId, int stockQuantity);

    boolean isStockAvailable(int variantId, int requiredQuantity);
    
}