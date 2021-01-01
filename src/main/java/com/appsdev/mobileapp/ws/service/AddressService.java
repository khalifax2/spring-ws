package com.appsdev.mobileapp.ws.service;

import com.appsdev.mobileapp.ws.shared.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAddresses(String userId);
    AddressDTO getAddress(String addressId);
}
