package com.fashionstore.model;

import java.math.BigDecimal;

public class OrderItem {

    private int orderItemId;
    private int orderId;
    private int productId;
    private int variantId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal total;

    public OrderItem() {
    }

    public OrderItem(int orderItemId, int orderId, int productId, int variantId,
                     int quantity, BigDecimal price, BigDecimal total) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    // Getters & Setters
    public int getOrderItemId() { return orderItemId; }
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getVariantId() { return variantId; }
    public void setVariantId(int variantId) { this.variantId = variantId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}