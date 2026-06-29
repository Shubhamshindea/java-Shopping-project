package com.fashionstore.dao;

import com.fashionstore.model.CartItem;
import java.util.List;

public interface CartItemDAO {

    boolean addCartItem(CartItem cartItem);

    boolean updateCartItemQuantity(int cartItemId, int quantity);

    boolean updateCartItemQuantityByCartAndVariant(int cartId, int variantId, int quantity);

    boolean removeCartItem(int cartItemId);

    boolean removeCartItemByCartAndVariant(int cartId, int variantId);

    CartItem getCartItemById(int cartItemId);

    CartItem getCartItemByCartIdAndVariantId(int cartId, int variantId);

    List<CartItem> getCartItemsByCartId(int cartId);

    boolean clearCart(int cartId);

    int getCartItemCount(int cartId);

	boolean updateCartItem(int cartItemId, int quantity);

	List<CartItem> getCartItems(int cartId);
}