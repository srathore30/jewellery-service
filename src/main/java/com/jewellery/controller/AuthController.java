package com.jewellery.controller;
import com.jewellery.AuthUtils.JwtHelper;
import com.jewellery.dto.req.User.ImageUploadDto;
import com.jewellery.dto.req.User.JwtRequest;
import com.jewellery.dto.req.User.UserRequestDto;
import com.jewellery.dto.req.User.UserUpdateRequestDto;
import com.jewellery.dto.res.User.JwtResponse;
import com.jewellery.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Setter
public class AuthController {

    @Autowired
    private JwtHelper helper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthService authService;


    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        String uuid = authService.createUser(userRequestDto);
        return new ResponseEntity<>(uuid, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<JwtResponse> update(@PathVariable Long id,   @RequestBody UserUpdateRequestDto userRequestDto){
        JwtResponse response= authService.updateUser(id,userRequestDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody JwtRequest jwtRequest) {
       String token = authService.userLogin(jwtRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/validateToken")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        try {
            if (helper.validateOnlyToken(token)) {
                return new ResponseEntity<>("valid token", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Not valid token", HttpStatus.UNAUTHORIZED);
            }
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("Expired token", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam Long id, @RequestParam String newPassword) {
        String resp = authService.resetPassword(id, newPassword);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }


    @PostMapping("/deleteAccount")
    public ResponseEntity<Void> deleteAccount(@RequestParam Long id) {
        authService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<String> verifyEmail(@RequestParam String email) {
        String res = authService.sendOtpToEmail(email);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/user/verifySignupOtp")
    public ResponseEntity<Long> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        Long resp = authService.verifyUserEmailOtp(email, otp);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadUserImage(@RequestBody ImageUploadDto imageUploadDto) {
        return new ResponseEntity<>(authService.uploadUserImage(imageUploadDto),HttpStatus.OK);
    }

    @DeleteMapping("/deleteImage")
    public ResponseEntity<String> deleteImage(@RequestParam String base64Url) {
        String response = authService.deleteImage(base64Url);
        return ResponseEntity.ok(response);
    }
}