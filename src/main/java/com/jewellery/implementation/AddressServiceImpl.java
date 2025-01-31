package com.jewellery.implementation;

import com.jewellery.constant.ApiErrorCodes;
import com.jewellery.dto.req.address.AddressRequest;
import com.jewellery.dto.res.address.AddressResponse;
import com.jewellery.entities.AddressEntity;
import com.jewellery.entities.UserEntity;
import com.jewellery.exception.NoSuchElementFoundException;
import com.jewellery.repositories.AddressRepo;
import com.jewellery.repositories.UserRepo;
import com.jewellery.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepository;
    private final UserRepo userRepository;

    @Override
    public AddressResponse createAddress(AddressRequest request) {
        UserEntity userEntity = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage()));

        AddressEntity addressEntity = mapDtoToEntity(request);
        addressEntity.setUserEntity(userEntity);
        AddressEntity savedEntity = addressRepository.save(addressEntity);

        return mapEntityToDto(savedEntity);
    }

    @Override
    public AddressResponse getAddressById(Long id) {
        AddressEntity addressEntity = addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.ADDRESS_NOT_FOUND.getErrorCode(), ApiErrorCodes.ADDRESS_NOT_FOUND.getErrorMessage()));

        return mapEntityToDto(addressEntity);
    }

    @Override
    public List<AddressResponse> getAllAddressByUserId(Long userId) {
        return addressRepository.findByUserEntityId(userId)
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddressResponse updateAddress(Long id, AddressRequest request) {
        AddressEntity addressEntity = addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.ADDRESS_NOT_FOUND.getErrorCode(), ApiErrorCodes.ADDRESS_NOT_FOUND.getErrorMessage()));

        updateEntityFromDto(addressEntity, request);
        AddressEntity updatedEntity = addressRepository.save(addressEntity);

        return mapEntityToDto(updatedEntity);
    }

    @Override
    public void deleteAddress(Long id) {
        AddressEntity addressEntity = addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.ADDRESS_NOT_FOUND.getErrorCode(), ApiErrorCodes.ADDRESS_NOT_FOUND.getErrorMessage()));

        addressRepository.delete(addressEntity);

    }


    @Override
    public AddressResponse getActiveAddressByUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage()));

        AddressEntity addressEntity = addressRepository.findActiveAddressByUserId(userId)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.ADDRESS_NOT_FOUND.getErrorCode(), ApiErrorCodes.ADDRESS_NOT_FOUND.getErrorMessage()));

        return mapEntityToDto(addressEntity);
    }

    @Override
    public AddressResponse SetDefaultAddress(Long addressId, Boolean isActive) {

        AddressEntity addressEntity = addressRepository.findById(addressId)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.ADDRESS_NOT_FOUND.getErrorCode(), ApiErrorCodes.ADDRESS_NOT_FOUND.getErrorMessage()));

        addressEntity.setIsActive(isActive);

        AddressEntity updatedAddressEntity = addressRepository.save(addressEntity);

        return mapEntityToDto(updatedAddressEntity);
    }



    private AddressEntity mapDtoToEntity(AddressRequest dto) {
        AddressEntity entity = new AddressEntity();
        entity.setName(dto.getName());
        entity.setMobileNo(dto.getMobileNo());
        entity.setLandmark(dto.getLandmark());
        entity.setAddress(dto.getAddress());
        entity.setPincode(dto.getPincode());
        entity.setState(dto.getState());
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());
        entity.setTag(dto.getTag());
        entity.setStatus(dto.getStatus());  // Assuming default status is ACTIVE
        entity.setIsActive(dto.getIsActive());
        return entity;
    }

    private AddressResponse mapEntityToDto(AddressEntity entity) {
        AddressResponse response = new AddressResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setMobileNo(entity.getMobileNo());
        response.setLandmark(entity.getLandmark());
        response.setAddress(entity.getAddress());
        response.setPincode(entity.getPincode());
        response.setState(entity.getState());
        response.setCity(entity.getCity());
        response.setCountry(entity.getCountry());
        response.setTag(entity.getTag());
        response.setStatus(entity.getStatus());
        response.setUserId(entity.getUserEntity().getId());
        response.setIsActive(entity.getIsActive());
        return response;
    }

    private void updateEntityFromDto(AddressEntity entity, AddressRequest dto) {
        entity.setName(dto.getName());
        entity.setMobileNo(dto.getMobileNo());
        entity.setLandmark(dto.getLandmark());
        entity.setAddress(dto.getAddress());
        entity.setPincode(dto.getPincode());
        entity.setState(dto.getState());
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());
        entity.setTag(dto.getTag());
        entity.setStatus(dto.getStatus());
        entity.setIsActive(dto.getIsActive());
    }


}
