package com.example.SwiggyClone.service.impl;

import com.example.SwiggyClone.dto.FoodItemDTO;
import com.example.SwiggyClone.dto.RestaurantDTO;
import com.example.SwiggyClone.entity.FoodItem;
import com.example.SwiggyClone.entity.Restaurant;
import com.example.SwiggyClone.exception.ResourceNotFoundException;
import com.example.SwiggyClone.repository.FoodItemRepository;
import com.example.SwiggyClone.repository.RestaurantRepository;
import com.example.SwiggyClone.service.inter.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final RestaurantRepository restaurantRepository;
    private final FoodItemRepository foodItemRepository;

    @Override
    @Transactional
    public RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = toEntity(restaurantDTO);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return toDto(savedRestaurant);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FoodItemDTO addFoodItemToMenu(Long restaurantId, FoodItemDTO foodItemDTO) {
        // 1. Find the restaurant to add the food item to.
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        // 2. Convert DTO to entity and establish the relationship.
        FoodItem foodItem = toEntity(foodItemDTO);
        foodItem.setRestaurant(restaurant);

        // 3. Save the new food item.
        FoodItem savedFoodItem = foodItemRepository.save(foodItem);
        return toDto(savedFoodItem);
    }

    // ================== MAPPING HELPERS ==================

    private RestaurantDTO toDto(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .cuisineType(restaurant.getCuisineType())
                .build();
    }

    private Restaurant toEntity(RestaurantDTO dto) {
        return Restaurant.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .cuisineType(dto.getCuisineType())
                .build();
    }

    private FoodItemDTO toDto(FoodItem foodItem) {
        return FoodItemDTO.builder()
                .id(foodItem.getId())
                .name(foodItem.getName())
                .description(foodItem.getDescription())
                .price(foodItem.getPrice())
                .restaurantId(foodItem.getRestaurant().getId())
                .build();
    }

    private FoodItem toEntity(FoodItemDTO dto) {
        return FoodItem.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();
    }
}