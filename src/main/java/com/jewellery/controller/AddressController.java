package com.jewellery.controller;

import com.jewellery.dto.req.address.AddressRequest;
import com.jewellery.dto.res.address.AddressResponse;
import com.jewellery.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest request) {
        AddressResponse addressResponse = addressService.createAddress(request);
        return new ResponseEntity<>(addressResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) {
        AddressResponse addressResponse = addressService.getAddressById(id);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponse>> getAllAddressByUserId(@PathVariable Long userId) {
        List<AddressResponse> addressResponse = addressService.getAllAddressByUserId(userId);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id, @RequestBody AddressRequest request) {
        AddressResponse addressResponse = addressService.updateAddress(id, request);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/active/user/{userId}")
    public ResponseEntity<AddressResponse> getActiveAddressByUser(@PathVariable Long userId) {
        AddressResponse addressResponse = addressService.getActiveAddressByUser(userId);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }


    @PutMapping("/{addressId}/set-default")
    public ResponseEntity<AddressResponse> setDefaultAddress(@PathVariable Long addressId, @RequestParam Boolean isActive) {
        AddressResponse addressResponse = addressService.SetDefaultAddress(addressId, isActive);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }
}
