package com.example.SwiggyClone.service.inter;

import com.example.SwiggyClone.dto.CartDTO;

public interface CartService {

    /**
     * Get cart details for a user.
     * @param userId user id
     * @return CartDTO
     */
    CartDTO getCartByUserId(Long userId);

    /**
     * Add an item to the user's cart.
     * @param userId user id
     * @param foodItemId food item id
     * @param quantity number of items
     * @return updated CartDTO
     */
    CartDTO addItemToCart(Long userId, Long foodItemId, int quantity);

    /**
     * Remove an item from the user's cart.
     * @param userId user id
     * @param foodItemId food item id
     * @return updated CartDTO
     */
    CartDTO removeItemFromCart(Long userId, Long foodItemId);

    /**
     * Clear the cart for a user.
     * @param userId user id
     */
    void clearCart(Long userId);
}
