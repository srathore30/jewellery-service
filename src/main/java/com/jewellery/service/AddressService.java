package com.jewellery.service;

import com.jewellery.dto.req.address.AddressRequest;
import com.jewellery.dto.res.address.AddressResponse;


import java.util.List;

public interface AddressService {

    AddressResponse createAddress(AddressRequest request);

    AddressResponse getAddressById(Long id);

    List<AddressResponse> getAllAddressByUserId(Long userId);

    AddressResponse updateAddress(Long id, AddressRequest request);

    void deleteAddress(Long id);

    AddressResponse getActiveAddressByUser(Long userId);

    AddressResponse SetDefaultAddress(Long addressId, Boolean isActive);
}
