package com.appsdev.mobileapp.ws.io.entity.repository;

import com.appsdev.mobileapp.ws.io.entity.AddressEntity;
import com.appsdev.mobileapp.ws.io.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    static boolean recordsCreated = false;

    @BeforeEach
    void setUp() {
        if (!recordsCreated) createRecord();
        
    }

    @Test
    void getVerifiedUsers() {
        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<UserEntity> pages = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
        assertNotNull(pages);

        List<UserEntity> userEntities = pages.getContent();
        assertNotNull(userEntities);
//        assertEquals(userEntities.size(), 2);
    }

    @Test
    void findUserByFirstName() {

        List<UserEntity> users = userRepository.findUserByFirstName("Sergey");
        assertNotNull(users);
        assertEquals(users.size(), 1);

        UserEntity user = users.get(0);
        assertEquals(user.getFirstName(), "Sergey");
    }

    @Test
    void findUserByLastName() {

        List<UserEntity> users = userRepository.findUserByLastName("Castro");
        assertNotNull(users);
        assertEquals(users.size(), 1);

        UserEntity user = users.get(0);
        assertEquals(user.getLastName(), "Castro");
    }

    @Test
    void findUserByKeyword() {
        String keyword = "tr";
        List<UserEntity> users = userRepository.findUsersByKeyword(keyword);
        assertNotNull(users);
        assertEquals(users.size(), 1);

        UserEntity user = users.get(0);
        assertTrue(user.getLastName().contains(keyword) || user.getFirstName().contains(keyword));
    }

    @Test
    void findUsersByFirstNameAndLastNameKeyword() {
        String keyword = "tr";
        List<Object[]> users = userRepository.findUsersByFirstNameAndLastNameKeyword(keyword);
        assertNotNull(users);
        assertEquals(users.size(), 1);

        Object[] user = users.get(0);

        String userFirstName = String.valueOf((user[0]));
        String userLastName = String.valueOf(user[1]);

        assertEquals(user.length, 2);
        assertNotNull(userFirstName);
        assertNotNull(userLastName);

        System.out.println("Firstname: " + userFirstName);
        System.out.println("Lastname: " + userLastName);

    }

    @Test
    void updateUserEmailVerificationStatus() {
        boolean newEmailVerificationStatus = false;
        userRepository.updateUserEmailVerificationStatus(newEmailVerificationStatus, "1asb2");
        UserEntity storedUserDetails = userRepository.findByUserId("1asb2");
        boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
        assertEquals(storedEmailVerificationStatus, newEmailVerificationStatus);
    }

    @Test
    void findUserEntityByUserId() {
        String userId = "1asb2";
        UserEntity userEntity = userRepository.findUserEntityByUserId(userId);

        assertNotNull(userEntity);
        assertEquals(userEntity.getUserId(), userId);
    }

    @Test
    void getUserEntityFullNameById() {
        String userId = "1asb2";
        List<Object[]> records = userRepository.getUserEntityFullNameById(userId);

        assertNotNull(records);
        assertEquals(records.size(), 1);

        Object[] user = records.get(0);

        String userFirstName = String.valueOf((user[0]));
        String userLastName = String.valueOf(user[1]);

        assertEquals(user.length, 2);
        assertNotNull(userFirstName);
        assertNotNull(userLastName);
    }

    @Test
    void updateUserEntityEmailVerificationStatus() {
        boolean newEmailVerificationStatus = false;
        userRepository.updateUserEntityEmailVerificationStatus(newEmailVerificationStatus, "1asb2");
        UserEntity storedUserDetails = userRepository.findByUserId("1asb2");
        boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();

        assertEquals(storedEmailVerificationStatus, newEmailVerificationStatus);
    }


    private void createRecord() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("1asb2");
        userEntity.setFirstName("Jericho");
        userEntity.setLastName("Castro");
        userEntity.setEncryptedPassword("x2123+2");
        userEntity.setEmail("test@test.com");
        userEntity.setEmailVerificationStatus(true);


        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setType("shipping");
        addressEntity.setAddressId("ahgyt74hfy");
        addressEntity.setCity("Vancouver");
        addressEntity.setCountry("Canada");
        addressEntity.setPostalCode("ABCCDA");
        addressEntity.setStreetName("123 Street Address");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(addressEntity);

        userEntity.setAddresses(addresses);

        userRepository.save(userEntity);


        // Prepare User Entity
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("Sergey");
        userEntity2.setLastName("Kargopolov");
        userEntity2.setUserId("1a2b3cddddd");
        userEntity2.setEncryptedPassword("xxx");
        userEntity2.setEmail("test1@test.com");
        userEntity2.setEmailVerificationStatus(true);

        // Prepare User Addresses
        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setType("shipping");
        addressEntity2.setAddressId("ahgyt74hfywwww");
        addressEntity2.setCity("Vancouver");
        addressEntity2.setCountry("Canada");
        addressEntity2.setPostalCode("ABCCDA");
        addressEntity2.setStreetName("123 Street Address");

        List<AddressEntity> addresses2 = new ArrayList<>();
        addresses2.add(addressEntity2);

        userEntity2.setAddresses(addresses2);

        userRepository.save(userEntity2);

        recordsCreated = true;
    }













}