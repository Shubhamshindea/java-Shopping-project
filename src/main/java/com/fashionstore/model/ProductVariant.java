package com.fashionstore.model;

public class ProductVariant {

    private int variantId;
    private int productId;
    private String size;
    private int stockQuantity;

    // Getter & Setter for variantId
    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    // Getter & Setter for productId
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    // Getter & Setter for size
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    // ✅ IMPORTANT FIX
    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}