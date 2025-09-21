package com.example.SwiggyClone.controller;

import com.example.SwiggyClone.dto.FoodItemDTO;
import com.example.SwiggyClone.dto.RestaurantDTO;
import com.example.SwiggyClone.service.inter.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/restaurants")
    public ResponseEntity<RestaurantDTO> addRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        return ResponseEntity.ok(adminService.addRestaurant(restaurantDTO));
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        return ResponseEntity.ok(adminService.getAllRestaurants());
    }

    @PostMapping("/restaurants/{restaurantId}/menu")
    public ResponseEntity<FoodItemDTO> addFoodItemToMenu(
            @PathVariable Long restaurantId,
            @Valid @RequestBody FoodItemDTO foodItemDTO) {
        return ResponseEntity.ok(adminService.addFoodItemToMenu(restaurantId, foodItemDTO));
    }
}
