package com.MTGCollectionTracker.MTGCollectionTrackerApplication.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * controller for login page
 */
@Controller
public class MTGCollectionTrackerLoginController {

    @GetMapping("/loginPage")
    public String showLoginPage() {

        return "Forms/LoginForm";
    }
}
