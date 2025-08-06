package com.example.SwiggyClone.service.impl;

import com.example.SwiggyClone.dto.FoodItemDTO;
import com.example.SwiggyClone.entity.FoodItem;
import com.example.SwiggyClone.entity.Restaurant;
import com.example.SwiggyClone.ResourceNotFoundException;
import com.example.SwiggyClone.repository.FoodItemRepository;
import com.example.SwiggyClone.repository.RestaurantRepository;
import com.example.SwiggyClone.service.inter.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodItemServiceImpl implements FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public FoodItemDTO createFoodItem(FoodItemDTO foodItemDTO) {
        Restaurant restaurant = restaurantRepository.findById(foodItemDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        FoodItem foodItem = FoodItem.builder()
                .name(foodItemDTO.getName())
                .price(foodItemDTO.getPrice())
                .restaurant(restaurant)
                .build();

        FoodItem saved = foodItemRepository.save(foodItem);
        foodItemDTO.setId(saved.getId());
        return foodItemDTO;
    }

    @Override
    public FoodItemDTO getFoodItemById(Long id) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found"));
        return convertToDTO(foodItem);
    }

    @Override
    public List<FoodItemDTO> getAllFoodItems() {
        return foodItemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FoodItemDTO updateFoodItem(Long id, FoodItemDTO foodItemDTO) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found"));

        foodItem.setName(foodItemDTO.getName());
        foodItem.setPrice(foodItemDTO.getPrice());
        foodItemRepository.save(foodItem);

        return convertToDTO(foodItem);
    }

    @Override
    public void deleteFoodItem(Long id) {
        foodItemRepository.deleteById(id);
    }

    private FoodItemDTO convertToDTO(FoodItem foodItem) {
        return FoodItemDTO.builder()
                .id(foodItem.getId())
                .name(foodItem.getName())
                .price(foodItem.getPrice())
                .restaurantId(foodItem.getRestaurant().getId())
                .build();
    }
}
