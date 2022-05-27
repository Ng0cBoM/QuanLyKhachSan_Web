package com.hotel.demo.controler;

import com.hotel.demo.constant.AppConstant;
import com.hotel.demo.dto.ResultDTO;
import com.hotel.demo.entity.User;
import com.hotel.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping(value = {"/user", "/auth"})
public class UserController {

    @Autowired
    private UserService UserService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        this.UserService.logout();
        return "redirect:/auth/login";
    }

    @PostMapping("/login")
    public String onLogin(@ModelAttribute("user") User user, Model model) {

        ResultDTO<User> result = this.UserService.login(user);
        if (result.isError()) {
            model.addAttribute("error", result.getMessage());
            return "login";
        }
        user = result.getData();
        if (user.getRole() == AppConstant.ROLE_GUEST) {
            return "redirect:/";
        }
        return "redirect:/owner/";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String onRegister(@ModelAttribute User user, Model model) {
        ResultDTO<User> result = this.UserService.save(user);
        if (result.isError()) {
            model.addAttribute("user", user);
            model.addAttribute("error", result.getMessage());
            return "register";
        }
        return "redirect:/auth/login";
    }
}