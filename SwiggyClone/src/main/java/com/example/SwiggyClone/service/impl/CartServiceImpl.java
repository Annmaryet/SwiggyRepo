package com.example.SwiggyClone.service.impl;

import com.example.SwiggyClone.dto.CartDTO;
import com.example.SwiggyClone.dto.CartItemDTO;
import com.example.SwiggyClone.entity.Cart;
import com.example.SwiggyClone.entity.CartItem;
import com.example.SwiggyClone.entity.FoodItem;
import com.example.SwiggyClone.entity.User;
import com.example.SwiggyClone.exception.ResourceNotFoundException;
import com.example.SwiggyClone.repository.CartRepository;
import com.example.SwiggyClone.repository.FoodItemRepository;
import com.example.SwiggyClone.repository.UserRepository;
import com.example.SwiggyClone.service.inter.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final FoodItemRepository foodItemRepository;

    private Cart findOrCreateCartByUserId(Long userId) {
        // CORRECTED: Now calls the correct repository method findByUserId
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setItems(new ArrayList<>());
            // We don't save it here. It will be saved when an item is added.
            return newCart;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public CartDTO getCartByUserId(Long userId) {
        Cart cart = findOrCreateCartByUserId(userId);
        return toDto(cart);
    }

    @Override
    @Transactional
    public CartDTO addItemToCart(Long userId, Long foodItemId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        Cart cart = findOrCreateCartByUserId(userId);
        FoodItem foodItem = foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new ResourceNotFoundException("FoodItem not found with id: " + foodItemId));

        Optional<CartItem> existingItemOptional = cart.getItems().stream()
                .filter(item -> item.getFoodItem().getId().equals(foodItemId))
                .findFirst();

        if (existingItemOptional.isPresent()) {
            CartItem existingItem = existingItemOptional.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setFoodItem(foodItem);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }

        Cart updatedCart = cartRepository.save(cart);
        return toDto(updatedCart);
    }

    @Override
    @Transactional
    public CartDTO removeItemFromCart(Long userId, Long foodItemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));

        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getFoodItem().getId().equals(foodItemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("FoodItem with id " + foodItemId + " not found in cart."));

        cart.getItems().remove(itemToRemove);

        Cart updatedCart = cartRepository.save(cart);
        return toDto(updatedCart);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        cartRepository.findByUserId(userId).ifPresent(cartRepository::delete);
    }

    // ================== PRIVATE MAPPING HELPERS ==================

    private CartDTO toDto(Cart cart) {
        // UPDATED: Now calculates the total price
        double totalPrice = cart.getItems().stream()
                .mapToDouble(item -> item.getFoodItem().getPrice() * item.getQuantity())
                .sum();

        List<CartItemDTO> itemDTOs = cart.getItems() != null
                ? cart.getItems().stream()
                .map(this::toDto)
                .collect(Collectors.toList())
                : new ArrayList<>();

        return CartDTO.builder()
                .userId(cart.getUser() != null ? cart.getUser().getId() : null)
                .items(itemDTOs)
                .totalPrice(totalPrice) // Set the calculated price
                .build();
    }

    // This method goes inside your CartServiceImpl.java
    private CartItemDTO toDto(CartItem item) {
        // This assumes your FoodItem entity has getName() and getPrice() methods.
        return CartItemDTO.builder()
                .foodItemId(item.getFoodItem().getId())
                .foodName(item.getFoodItem().getName()) // Populates the food name
                .price(item.getFoodItem().getPrice())     // Populates the price per item
                .quantity(item.getQuantity())
                .build();
    }
}