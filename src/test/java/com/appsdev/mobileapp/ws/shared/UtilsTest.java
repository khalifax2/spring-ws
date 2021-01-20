package com.appsdev.mobileapp.ws.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest {

    @Autowired
    Utils utils;

    @BeforeEach
    void setUp() {
    }

    @Test
    void generateUserId() {
        String userId = utils.generateUserId(30);
        String userId2 = utils.generateUserId(30);

        assertNotNull(userId);
        assertNotNull(userId2);

        assertTrue(userId.length() == 30);
        assertTrue(!userId.equals(userId2));


    }

    @Test
    void hasTokenNotExpired() {
        String token = utils.generateEmailVerificationToken("4asdA@12321");
        assertNotNull(token);

        boolean hasTokenExpired = utils.hasTokenExpired(token);
        assertFalse(hasTokenExpired);
    }

    @Test
    void hasTokenExpired() {
        String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2YlVveDA3OU1BVkF5MlhpazlZYU9SbXlQVjFGb3oiLCJleHAiOjE2MTA5NTE2ODd9.6Ryaj8Mvu8ADHfHzs0cr-rAApHHpKLJXJvafYDRCK8WEmgEr0DSEvuF6ps8KSpUpNPsHoR_S8-j-g1abE5Af5g"; // INPUT RANDOM EXPIRED TOKEN

        boolean hasTokenExpired = utils.hasTokenExpired(expiredToken);
        assertTrue(hasTokenExpired);
    }
}