package com.jewellery.implementation;

import com.jewellery.AuthUtils.JwtHelper;
import com.jewellery.Configs.AuthConfig;
import com.jewellery.constant.ApiErrorCodes;
import com.jewellery.constant.Role;
import com.jewellery.constant.Status;
import com.jewellery.dto.req.User.ImageUploadDto;
import com.jewellery.dto.req.User.JwtRequest;
import com.jewellery.dto.req.User.UserRequestDto;
import com.jewellery.dto.req.User.UserUpdateRequestDto;
import com.jewellery.dto.res.User.JwtResponse;
import com.jewellery.email.EmailOtpService;
import com.jewellery.email.EmailService;
import com.jewellery.entities.UserDetailsEntity;
import com.jewellery.entities.UserEntity;
import com.jewellery.exception.NoSuchElementFoundException;
import com.jewellery.exception.UserAlreadyExistsException;
import com.jewellery.exception.ValidationException;
import com.jewellery.repositories.UserRepo;
import com.jewellery.service.AuthService;
import com.jewellery.util.ImageUploader;
import com.jewellery.util.Validator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ImageUploader imageUploader;
    @Autowired
    private AuthConfig authConfig;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private EmailOtpService emailOtpService;

    @Override
    @Transactional
    public String createUser(UserRequestDto userRequestDto) {
        Optional<UserEntity> foundUser = userRepo.findByMobileNo(userRequestDto.getMobile());
        Optional<UserEntity> foundUserWithEmail = userRepo.findByEmail(userRequestDto.getEmail());
        if (foundUser.isPresent() || foundUserWithEmail.isPresent()) {
            throw new UserAlreadyExistsException(ApiErrorCodes.USER_ALREADY_EXIST.getErrorCode(), ApiErrorCodes.USER_ALREADY_EXIST.getErrorMessage());
        }
        if (!Validator.isValidMobileNo(userRequestDto.getMobile())) {
            throw new ValidationException(ApiErrorCodes.INVALID_MOBILE_NUMBER.getErrorCode(), ApiErrorCodes.INVALID_MOBILE_NUMBER.getErrorMessage());
        }
        UserEntity user = this.mapDtoToEntity(userRequestDto);
        userRepo.save(user);
        return user.getName();
    }

    @Override
    public void deleteAccount(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        if (optionalUserEntity.isEmpty()) {
            throw new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage());
        }
        optionalUserEntity.get().setStatus(Status.INACTIVE);
        userRepo.save(optionalUserEntity.get());
        String subject = "Account Deletion Notification";
        String messageBody = "Dear " + optionalUserEntity.get().getName() + ",\n\n" + "Your account has been successfully deleted. ";
        emailService.sendEmail(optionalUserEntity.get().getEmail(), subject, messageBody);

    }

    @Override
    public JwtResponse updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        if (optionalUserEntity.isPresent()) {
            UserEntity user = optionalUserEntity.get();
            updateEntityFromDto(user, userUpdateRequestDto);
            return mapEntityToDto(userRepo.save(user));
        }
        throw new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage());
    }

    @Override
    public UserDetailsEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userDetail = userRepo.findByMobileNo(Long.valueOf(username));
        if (userDetail.isEmpty()) {
            throw new NoSuchElementFoundException(ApiErrorCodes.INVALID_USERNAME_OR_PASSWORD.getErrorCode(), ApiErrorCodes.INVALID_USERNAME_OR_PASSWORD.getErrorMessage());
        }
        return mapToUserDetails(userDetail.get());
    }

    @Override
    public String userLogin(JwtRequest request) {
        UserDetails userDetails = loadUserByUsername(String.valueOf(request.getMobile()));
        if (authConfig.matches(request.getPassword(), userDetails.getPassword())) {
            return jwtHelper.generateToken(userDetails);
        }
        throw new ValidationException(ApiErrorCodes.INVALID_USERNAME_OR_PASSWORD.getErrorCode(), ApiErrorCodes.INVALID_USERNAME_OR_PASSWORD.getErrorMessage());
    }


    @Override
    public String resetPassword(Long id, String newPassword) {
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        if (optionalUserEntity.isEmpty()) {
            throw new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage());
        }
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setPassword(authConfig.passwordEncoder().encode(newPassword));
        userRepo.save(userEntity);
        emailService.sendPasswordResetEmail(userEntity.getEmail(), userEntity.getName());
        return "password changed";
    }

    public UserEntity mapDtoToEntity(UserRequestDto userReqDto) {
        UserEntity userEntity = new UserEntity();
        List<Role> roleList = userReqDto.getUserType();
        userEntity.setRoleList(roleList);
        userEntity.setEmail(userReqDto.getEmail());
        if (userReqDto.getUserPhotoUrl() != null) {
            userEntity.setImageUrl(imageUploader.uploadFile(userReqDto.getUserPhotoUrl()));
        }
        userEntity.setMobileNo(userReqDto.getMobile());
        userEntity.setPassword(authConfig.passwordEncoder().encode(userReqDto.getPassword()));
        userEntity.setName(userReqDto.getName());
        userEntity.setStatus(userReqDto.getStatus());
        if (CollectionUtils.isEmpty(userReqDto.getUserType())) {
            throw new ValidationException(ApiErrorCodes.ROLE_LIST_NOT_PRESENT.getErrorCode(), ApiErrorCodes.ROLE_LIST_NOT_PRESENT.getErrorMessage());
        }
        userEntity.setRoleList(userReqDto.getUserType());
        return userEntity;
    }

    public UserDetailsEntity mapToUserDetails(UserEntity user) {
        UserDetailsEntity userDetails = new UserDetailsEntity();
        List<Role> roleList = user.getRoleList();
        userDetails.setMobileNo(user.getMobileNo());
        return userDetails;
    }

    public JwtResponse mapEntityToDto(UserEntity user) {
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setStatus(user.getStatus());
        jwtResponse.setImageUrl(user.getImageUrl());
        jwtResponse.setUserName(user.getName());
        return jwtResponse;
    }

    public void updateEntityFromDto(UserEntity userEntity, UserUpdateRequestDto userUpdateRequestDto) {

        userEntity.setRoleList(userUpdateRequestDto.getUserType());
        userEntity.setEmail(userUpdateRequestDto.getEmail());
        userEntity.setMobileNo(userUpdateRequestDto.getMobile());
        if (!Objects.equals(userUpdateRequestDto.getProfilePhoto(), "")) {
            userEntity.setImageUrl(imageUploader.uploadFile(userUpdateRequestDto.getProfilePhoto()));
        }
        userEntity.setName(userUpdateRequestDto.getName());
        userEntity.setStatus(userUpdateRequestDto.getStatus());
        if (userUpdateRequestDto.getUserType() == null || userUpdateRequestDto.getUserType().isEmpty()) {
            throw new ValidationException(ApiErrorCodes.ROLE_LIST_NOT_PRESENT.getErrorCode(), ApiErrorCodes.ROLE_LIST_NOT_PRESENT.getErrorMessage());
        }
        userEntity.setRoleList(userUpdateRequestDto.getUserType());
    }

    @Override
    public String sendOtpToEmail(String email) {
        emailOtpService.sendEmailCode(email);
        return "otp send";
    }

    @Override
    public Long verifyUserEmailOtp(String email, String otp) {
        Optional<UserEntity> optionalClients = userRepo.findByEmail(email);
        if (optionalClients.isEmpty()) {
            throw new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage());
        }
        boolean isValid = emailOtpService.verifyEmailCode(email, otp);
        if (!isValid) {
            throw new NoSuchElementFoundException(ApiErrorCodes.INVALID_EMAIL_CODE.getErrorCode(), ApiErrorCodes.INVALID_EMAIL_CODE.getErrorMessage());
        }
        return optionalClients.get().getId();
    }

    @Override
    public String uploadUserImage(ImageUploadDto imageUploadDto) {
        Optional<UserEntity> optionalUserEntity = userRepo.findById(imageUploadDto.getUserId());
        if (optionalUserEntity.isEmpty()) {
            throw new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage());
        }
        String imageUrl = imageUploader.uploadFile(imageUploadDto.getUserPhotoUrl());
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setImageUrl(imageUrl);
        userRepo.save(userEntity);
        return imageUrl;
    }
    @Override
    public String deleteImage(String base64Url) {
        imageUploader.deleteImage(base64Url);
        return "Image deleted successfully";
    }

}

