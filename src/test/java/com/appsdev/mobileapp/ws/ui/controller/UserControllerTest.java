package com.appsdev.mobileapp.ws.ui.controller;

import com.appsdev.mobileapp.ws.service.UserService;
import com.appsdev.mobileapp.ws.shared.dto.AddressDTO;
import com.appsdev.mobileapp.ws.shared.dto.UserDto;
import com.appsdev.mobileapp.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    UserDto userDto;

    final String USER_ID = "ey%23-Hfg569k2TKlksd,";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userDto = new UserDto();
        userDto.setFirstName("Jericho");
        userDto.setLastName("Castro");
        userDto.setEmail("test@test.com");
        userDto.setEmailVerificationStatus(Boolean.FALSE);
        userDto.setEmailVerificationToken(null);
        userDto.setUserId(USER_ID);
        userDto.setAddresses(getAddressesDto());
        userDto.setEncryptedPassword("xxJS21sJdb4512osk");
    }

    @Test
    void getUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);

        UserRest userRest = userController.getUser(USER_ID);
        assertNotNull(userRest);
        assertEquals(userDto.getFirstName(), userRest.getFirstName());
        assertEquals(userDto.getLastName(), userRest.getLastName());
        assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());
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
}