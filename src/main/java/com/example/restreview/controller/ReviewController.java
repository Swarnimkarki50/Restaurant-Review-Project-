package com.example.restreview.controller;

import com.example.restreview.entity.Member;
import com.example.restreview.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/add")
    public String addReview(@RequestParam Long restaurantId, @RequestParam String content, @RequestParam int rating, HttpSession session ) {
        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        reviewService.addReview(restaurantId, loginMember, content, rating);

        return "redirect:/restaurants/" + restaurantId;
    }
}