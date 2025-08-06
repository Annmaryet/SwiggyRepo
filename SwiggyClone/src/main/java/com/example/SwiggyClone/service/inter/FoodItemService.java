package com.example.SwiggyClone.service.inter;

import com.example.SwiggyClone.dto.FoodItemDTO;
import java.util.List;

public interface FoodItemService {
    FoodItemDTO createFoodItem(FoodItemDTO foodItemDTO);
    FoodItemDTO getFoodItemById(Long id);
    List<FoodItemDTO> getAllFoodItems();
    FoodItemDTO updateFoodItem(Long id, FoodItemDTO foodItemDTO);
    void deleteFoodItem(Long id);
}
