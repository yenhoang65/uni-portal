package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.User.UpdatePasswordDTO;
import io.spring.uni_portal.dto.User.UserProfileDTO;
import io.spring.uni_portal.dto.User.UserProfileUpdateDTO;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.UserService.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public Response<UserProfileDTO> getUserProfile(HttpServletRequest request) {
        return userService.getUserProfile(request);
    }

    @PutMapping("/profile")
    public Response<UserProfileUpdateDTO> updateUserProfile(HttpServletRequest request,
                                                            @RequestBody UserProfileUpdateDTO userProfileUpdateDTO) {
        return userService.updateUserProfile(request, userProfileUpdateDTO);
    }

    @PutMapping("/password")
    public Response<String> updatePassword(HttpServletRequest request,
                                           @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        return userService.updatePassword(request, updatePasswordDTO);
    }
}
