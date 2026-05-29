package com.example.restreview.controller;

import com.example.restreview.entity.Restaurant;
import com.example.restreview.service.RestaurantService;
import com.example.restreview.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("restaurants", restaurantService.findAll());
        return "restaurant/list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession session) {
        if (session.getAttribute("loginMember") == null) {
            return "redirect:/member/login";
        }

        return "restaurant/add";
    }

    @PostMapping("/add")
    public String addRestaurant(
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam String location,
            @RequestParam MultipartFile image,
            HttpSession session
    ) {
        if (session.getAttribute("loginMember") == null) {
            return "redirect:/member/login";
        }

        restaurantService.addRestaurant(name, category, location, image);
        return "redirect:/restaurants";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.findById(id);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("reviews", reviewService.findReviews(restaurant));
        model.addAttribute("averageRating", reviewService.getAverageRating(restaurant));

        return "restaurant/detail";
    }
}