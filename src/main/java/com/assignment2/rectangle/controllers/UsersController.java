package com.assignment2.rectangle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment2.rectangle.models.User;
import com.assignment2.rectangle.models.UserRepository;

import java.util.List;
import java.util.Map;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("users/view")
    public String getAllUsers(Model model) {
        System.out.println("Getting all users");
        List<User> users = userRepo.findAll();
        model.addAttribute("us", users);
        return "users/showAll";
    }

    @PostMapping("users/add")
    public String addRectangle(@RequestParam Map<String, String> newRectangle) {
        System.out.println("Add rectangle");
        String newName = newRectangle.get("name");
        int newWidth = Integer.parseInt(newRectangle.get("width"));
        int newHeight = Integer.parseInt(newRectangle.get("height"));
        String newColor = newRectangle.get("color");

        userRepo.save(new User(newName, newWidth, newHeight, newColor));
        return "users/addedRectangle";
    }

    @GetMapping("/rectangle-form")
    public String showRectangleForm() {
        return "users/rectangle-form";
    }

    @GetMapping("/rectangle-update-form")
    public String showUpdateRectangleForm(@RequestParam("id") int id, Model model) {
        User user = userRepo.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "users/update-rectangle-form";
    }

    @Transactional
    @PostMapping("/users/update")
    public String updateRectangle(@RequestParam Map<String, String> updatedRectangle) {
        int id = Integer.parseInt(updatedRectangle.get("id"));
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            user.setName(updatedRectangle.get("name"));
            user.setColor(updatedRectangle.get("color"));
            user.setWidth(Integer.parseInt(updatedRectangle.get("width")));
            user.setHeight(Integer.parseInt(updatedRectangle.get("height")));
            userRepo.save(user);
        }
        return "redirect:/users/view";
    }

    @Transactional
    @PostMapping("/rectangles/delete")
    public String deleteRectangle(@RequestParam int id) {
        System.out.println("Deleting rectangle with id: " + id);
        userRepo.deleteById(id);
        return "redirect:/users/view";
    }
}
