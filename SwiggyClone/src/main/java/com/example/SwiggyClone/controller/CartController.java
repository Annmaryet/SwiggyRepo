package com.example.SwiggyClone.controller;

import com.example.SwiggyClone.dto.CartDTO;
import com.example.SwiggyClone.service.inter.CartService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/{userId}/add/{foodItemId}")
    public ResponseEntity<CartDTO> addItemToCart(
            @PathVariable Long userId,
            @PathVariable Long foodItemId,
            @RequestParam(defaultValue = "1") int quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, foodItemId, quantity));
    }

    @DeleteMapping("/{userId}/remove/{foodItemId}")
    public ResponseEntity<CartDTO> removeItemFromCart(
            @PathVariable Long userId,
            @PathVariable Long foodItemId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(userId, foodItemId));
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable @NotNull Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
