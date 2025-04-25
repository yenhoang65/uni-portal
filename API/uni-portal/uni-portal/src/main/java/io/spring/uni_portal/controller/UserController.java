package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.User.UserProfileDTO;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.UserService.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public Response<UserProfileDTO> getUserProfile(HttpServletRequest request) {
        return userService.getUserProfile(request);
    }
}
