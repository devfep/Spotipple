package com.devfep.spotipple.controller;

import com.devfep.spotipple.entity.User;
import com.devfep.spotipple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("public")
public class PublicController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public String displayRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value = "/create-user", method = {RequestMethod.POST})
    public String createUser(@Valid @ModelAttribute("user") User user, Errors errors) {
        if (errors.hasErrors()) {
            return "register.html";
        }

        boolean isSaved = userService.createNewUser(user);

        if (isSaved) {
            return "redirect:/login?register=true";
        } else {
            return "register.html";
        }


    }
}
