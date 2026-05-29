package com.example.restreview.service;

import com.example.restreview.entity.Restaurant;
import com.example.restreview.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public void addRestaurant(String name, String category, String location, MultipartFile image) {
        String imageName = null;

        try {
            if (!image.isEmpty()) {
                String originalName = image.getOriginalFilename();
                imageName = UUID.randomUUID() + "_" + originalName;

                File uploadDir = new File(System.getProperty("user.dir") + "/uploads/");

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                File saveFile = new File(uploadDir, imageName);
                image.transferTo(saveFile);
            }

            Restaurant restaurant = Restaurant.builder()
                    .name(name)
                    .category(category)
                    .location(location)
                    .imageName(imageName)
                    .build();

            restaurantRepository.save(restaurant);

        } catch (Exception e) {
            throw new RuntimeException("Restaurant image upload error", e);
        }
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAllByOrderByIdDesc();
    }

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
    }
}