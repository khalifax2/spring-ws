package com.appsdev.mobileapp.ws.service.impl;

import com.appsdev.mobileapp.ws.exceptions.UserServiceException;
import com.appsdev.mobileapp.ws.io.entity.AddressEntity;
import com.appsdev.mobileapp.ws.io.entity.UserEntity;
import com.appsdev.mobileapp.ws.io.entity.repository.UserRepository;
import com.appsdev.mobileapp.ws.shared.AmazonSES;
import com.appsdev.mobileapp.ws.shared.Utils;
import com.appsdev.mobileapp.ws.shared.dto.AddressDTO;
import com.appsdev.mobileapp.ws.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    AmazonSES amazonSES;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    String userId = "123217";
    String encryptedPassword = "7eyasSSDa5sx5gg87234dfsdfsd-43432fsdfsdfs322";
    UserEntity userEntity;
    String email = "test@test.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUserId(userId);
        userEntity.setFirstName("Jericho");
        userEntity.setLastName("Castro");
        userEntity.setEncryptedPassword(encryptedPassword);
        userEntity.setEmail(email);
        userEntity.setEmailVerificationToken("akK2s-124sm*123Jsdasadas");
        userEntity.setAddresses(getAddressEntity());
    }

    @Test
    void getUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getUser(email);

        assertNotNull(userDto);
        assertEquals(userEntity.getFirstName(), userDto.getFirstName());

    }

    @Test
    void getUser_UsernameNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> userService.getUser(email));
    }

    @Test
    void createUser_UserServiceException() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = new UserDto();
        userDto.setAddresses(getAddressesDto());
        userDto.setFirstName("Jericho");
        userDto.setLastName("Castro");
        userDto.setPassword("123");
        userDto.setEmail(email);

        assertThrows(UserServiceException.class,
                () -> userService.createUser(userDto));
    }

    @Test
    void createUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("213121");
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDto.class));

        UserDto userDto = new UserDto();
        userDto.setAddresses(getAddressesDto());
        userDto.setFirstName("Jericho");
        userDto.setLastName("Castro");
        userDto.setPassword("123");
        userDto.setEmail(email);

        UserDto storedUserDetails = userService.createUser(userDto);
        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
        assertNotNull(storedUserDetails.getUserId());
        assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());
        verify(utils, times(2)).generateAddressId(30);
        verify(bCryptPasswordEncoder, times(1)).encode("123");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    private List<AddressDTO> getAddressesDto() {

        AddressDTO shippingAddressDTO = new AddressDTO();
        shippingAddressDTO.setType("shipping");
        shippingAddressDTO.setCity("Marikina");
        shippingAddressDTO.setCountry("Philippines");
        shippingAddressDTO.setPostalCode("1800");
        shippingAddressDTO.setStreetName("Gallant 7");

        AddressDTO billingAddressDTO = new AddressDTO();
        billingAddressDTO.setType("billing");
        billingAddressDTO.setCity("Montalban");
        billingAddressDTO.setCountry("Philippines");
        billingAddressDTO.setPostalCode("2600");
        billingAddressDTO.setStreetName("Southville 7");

        List<AddressDTO> addresses = new ArrayList<>();
        addresses.add(shippingAddressDTO);
        addresses.add(billingAddressDTO);

        return addresses;
    }

    private List<AddressEntity> getAddressEntity() {
        List<AddressDTO> addresses = getAddressesDto();

        Type listType = new TypeToken<List<AddressEntity>>() {}.getType();

        return new ModelMapper().map(addresses, listType);
    }
}