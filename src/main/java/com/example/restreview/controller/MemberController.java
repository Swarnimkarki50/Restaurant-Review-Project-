package com.example.restreview.controller;

import com.example.restreview.entity.Member;
import com.example.restreview.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signupForm() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {
        try {
            memberService.signup(name, email, password);
            return "redirect:/member/login";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "member/signup";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        Member loginMember = memberService.login(email, password);

        if (loginMember == null) {
            model.addAttribute("error", "Invalid email or password.");
            return "member/login";
        }

        session.setAttribute("loginMember", loginMember);
        return "redirect:/restaurants";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}