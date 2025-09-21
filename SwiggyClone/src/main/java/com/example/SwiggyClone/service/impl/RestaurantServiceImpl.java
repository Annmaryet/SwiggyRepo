package com.example.SwiggyClone.service.impl;

import com.example.SwiggyClone.dto.RestaurantDTO;
import com.example.SwiggyClone.dto.FoodItemDTO;
import com.example.SwiggyClone.entity.Restaurant;
import com.example.SwiggyClone.entity.FoodItem;
import com.example.SwiggyClone.exception.ResourceNotFoundException;
import com.example.SwiggyClone.repository.RestaurantRepository;
import com.example.SwiggyClone.repository.FoodItemRepository;
import com.example.SwiggyClone.service.inter.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FoodItemRepository foodItemRepository;

    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = Restaurant.builder()
                .name(restaurantDTO.getName())
                .address(restaurantDTO.getAddress())
                .location(restaurantDTO.getLocation())
                .cuisineType(restaurantDTO.getCuisineType())
                .build();

        Restaurant saved = restaurantRepository.save(restaurant);
        return convertToDTO(saved);
    }

    @Override
    public RestaurantDTO getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + id));
        return convertToDTO(restaurant);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + id));

        restaurant.setName(restaurantDTO.getName());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setLocation(restaurantDTO.getLocation());
        restaurant.setCuisineType(restaurantDTO.getCuisineType());

        Restaurant updated = restaurantRepository.save(restaurant);
        return convertToDTO(updated);
    }

    @Override
    public void deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurant not found with ID: " + id);
        }
        restaurantRepository.deleteById(id);
    }

    @Override
    public FoodItemDTO addMenuItemToRestaurant(Long restaurantId, FoodItemDTO foodItemDTO) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + restaurantId));

        FoodItem foodItem = FoodItem.builder()
                .name(foodItemDTO.getName())
                .price(foodItemDTO.getPrice())
                .restaurant(restaurant)
                .build();

        FoodItem saved = foodItemRepository.save(foodItem);
        return FoodItemDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .price(saved.getPrice())
                .restaurantId(restaurantId)
                .build();
    }

    @Override
    public List<FoodItemDTO> getMenuItemsByRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + restaurantId));

        return restaurant.getFoodItems().stream()
                .map(item -> FoodItemDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .price(item.getPrice())
                        .restaurantId(restaurantId)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDTO> searchRestaurantsByLocation(String location) {
        return restaurantRepository.findByLocationContainingIgnoreCase(location)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RestaurantDTO convertToDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .location(restaurant.getLocation())
                .cuisineType(restaurant.getCuisineType())
                .build();
    }
}
