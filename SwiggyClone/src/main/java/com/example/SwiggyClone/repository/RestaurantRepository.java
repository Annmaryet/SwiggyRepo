package com.example.SwiggyClone.repository;

import com.example.SwiggyClone.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // ✅ Custom search query
    List<Restaurant> findByLocationContainingIgnoreCase(String location);
}
