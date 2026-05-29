package com.example.restreview.service;

import com.example.restreview.entity.Member;
import com.example.restreview.entity.Restaurant;
import com.example.restreview.entity.Review;
import com.example.restreview.repository.RestaurantRepository;
import com.example.restreview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    public void addReview(Long restaurantId, Member member, String content, int rating) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        Review review = Review.builder()
                .restaurant(restaurant)
                .member(member)
                .content(content)
                .rating(rating)
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
    }

    public List<Review> findReviews(Restaurant restaurant) {
        return reviewRepository.findByRestaurantOrderByIdDesc(restaurant);
    }

    public double getAverageRating(Restaurant restaurant) {
        List<Review> reviews = findReviews(restaurant);

        if (reviews.isEmpty()) {
            return 0;
        }

        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0);
    }
}