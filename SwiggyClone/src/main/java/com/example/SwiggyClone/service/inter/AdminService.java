package com.example.SwiggyClone.service.inter;

import com.example.SwiggyClone.dto.FoodItemDTO;
import com.example.SwiggyClone.dto.RestaurantDTO;

import java.util.List;

public interface AdminService {

    RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO);

    List<RestaurantDTO> getAllRestaurants();

    FoodItemDTO addFoodItemToMenu(Long restaurantId, FoodItemDTO foodItemDTO);
}