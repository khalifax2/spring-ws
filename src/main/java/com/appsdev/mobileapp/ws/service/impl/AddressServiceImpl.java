package com.appsdev.mobileapp.ws.service.impl;

import com.appsdev.mobileapp.ws.io.entity.AddressEntity;
import com.appsdev.mobileapp.ws.io.entity.UserEntity;
import com.appsdev.mobileapp.ws.io.entity.repository.AddressRepository;
import com.appsdev.mobileapp.ws.io.entity.repository.UserRepository;
import com.appsdev.mobileapp.ws.service.AddressService;
import com.appsdev.mobileapp.ws.shared.dto.AddressDTO;
import org.apache.tomcat.jni.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public AddressServiceImpl(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public List<AddressDTO> getAddresses(String userId) {
        List<AddressDTO> returnValue = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) return returnValue;

        // Find address by AddressRepo || user.getAddress()
        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);

        for (AddressEntity addressEntity : addresses) {
            returnValue.add(new ModelMapper().map(addressEntity, AddressDTO.class));
        }

        return returnValue;
    }

    @Override
    public AddressDTO getAddress(String addressId) {
        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

        if (addressEntity == null) return null;

        AddressDTO returnValue = new ModelMapper().map(addressEntity, AddressDTO.class);

        return returnValue;
    }
}
