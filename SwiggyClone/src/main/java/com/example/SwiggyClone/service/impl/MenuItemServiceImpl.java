package com.example.SwiggyClone.service.impl;

import com.example.SwiggyClone.dto.MenuItemDTO;
import com.example.SwiggyClone.entity.MenuItem;
import com.example.SwiggyClone.entity.Restaurant;
import com.example.SwiggyClone.ResourceNotFoundException;
import com.example.SwiggyClone.repository.MenuItemRepository;
import com.example.SwiggyClone.repository.RestaurantRepository;
import com.example.SwiggyClone.service.inter.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO) {
        Restaurant restaurant = restaurantRepository.findById(menuItemDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        MenuItem menuItem = MenuItem.builder()
                .name(menuItemDTO.getName())
                .price(menuItemDTO.getPrice())
                .restaurant(restaurant)
                .build();

        MenuItem saved = menuItemRepository.save(menuItem);
        menuItemDTO.setId(saved.getId());
        return menuItemDTO;
    }

    @Override
    public List<MenuItemDTO> getAllMenuItems() {
        return menuItemRepository.findAll()
                .stream()
                .map(item -> new MenuItemDTO(item.getId(), item.getName(), item.getPrice(), item.getRestaurant().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemDTO getMenuItemById(Long id) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
        return new MenuItemDTO(item.getId(), item.getName(), item.getPrice(), item.getRestaurant().getId());
    }

    @Override
    public MenuItemDTO updateMenuItem(Long id, MenuItemDTO menuItemDTO) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        item.setName(menuItemDTO.getName());
        item.setPrice(menuItemDTO.getPrice());
        MenuItem updated = menuItemRepository.save(item);

        return new MenuItemDTO(updated.getId(), updated.getName(), updated.getPrice(), updated.getRestaurant().getId());
    }

    @Override
    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}
