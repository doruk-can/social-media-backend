package com.group308.socialmedia;

import com.group308.socialmedia.core.security.PasswordService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PasswordServiceTests {

    @Autowired
    private PasswordService passwordService;

    private String encryptPassword;

    @Before
    public void setup(){
        encryptPassword = passwordService.encryptPassword("password");
    }

    @Test
    public void decryptPassword() {
        String pass = passwordService.decryptPassword(encryptPassword);
        System.out.println("pass:" + pass);
        System.out.println("encryptPassword:" + encryptPassword);
    }

}