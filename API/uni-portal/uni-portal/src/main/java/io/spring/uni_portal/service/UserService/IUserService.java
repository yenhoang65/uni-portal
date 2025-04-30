package io.spring.uni_portal.service.UserService;

import io.spring.uni_portal.dto.User.UpdatePasswordDTO;
import io.spring.uni_portal.dto.User.UserProfileDTO;
import io.spring.uni_portal.dto.User.UserProfileUpdateDTO;
import io.spring.uni_portal.response.Response;
import jakarta.servlet.http.HttpServletRequest;

public interface IUserService {
    Response<UserProfileDTO> getUserProfile(HttpServletRequest request);
    Response<String> updatePassword(HttpServletRequest request, UpdatePasswordDTO updatePasswordDTO);
    Response<UserProfileUpdateDTO> updateUserProfile(HttpServletRequest request, UserProfileUpdateDTO userProfileUpdateDTO);
}
