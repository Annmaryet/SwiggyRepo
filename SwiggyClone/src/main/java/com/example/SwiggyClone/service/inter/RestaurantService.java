package com.example.SwiggyClone.service.inter;

import com.example.SwiggyClone.dto.RestaurantDTO;
import com.example.SwiggyClone.dto.FoodItemDTO;

import java.util.List;

public interface RestaurantService {

    RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO);

    RestaurantDTO getRestaurantById(Long id);

    List<RestaurantDTO> getAllRestaurants();

    RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO);

    void deleteRestaurant(Long id);

    FoodItemDTO addMenuItemToRestaurant(Long restaurantId, FoodItemDTO foodItemDTO);

    List<FoodItemDTO> getMenuItemsByRestaurant(Long restaurantId);

    List<RestaurantDTO> searchRestaurantsByLocation(String location);
}
