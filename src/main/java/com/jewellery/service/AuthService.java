package com.jewellery.service;

import com.jewellery.dto.req.User.ImageUploadDto;
import com.jewellery.dto.req.User.JwtRequest;
import com.jewellery.dto.req.User.UserRequestDto;
import com.jewellery.dto.req.User.UserUpdateRequestDto;
import com.jewellery.dto.res.User.JwtResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService{
    String createUser(UserRequestDto userRequestDto);
    void deleteAccount(Long id);

    JwtResponse updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto);

    String userLogin(JwtRequest request);

    String resetPassword(Long id, String newPassword);

    Long verifyUserEmailOtp(String email, String otp);

}