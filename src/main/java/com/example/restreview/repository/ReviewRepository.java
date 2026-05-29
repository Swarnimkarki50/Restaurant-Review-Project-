package com.example.restreview.repository;

import com.example.restreview.entity.Restaurant;
import com.example.restreview.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByRestaurantOrderByIdDesc(Restaurant restaurant);
}