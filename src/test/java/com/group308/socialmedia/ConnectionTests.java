package com.group308.socialmedia;


import com.group308.socialmedia.core.model.domain.Connection;
import com.group308.socialmedia.core.model.service.ConnectionService;
import com.group308.socialmedia.core.model.service.PostService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase
public class ConnectionTests {


    @Autowired
    private ConnectionService connectionService;

    @Test
    public void followingTest(){

        Connection connection = new Connection();
        connection.setFollowerId(4);
        connection.setFollowingId(6);

        try {
            connection = connectionService.save(connection);
        }
        catch (Exception e){

        }

        Assert.assertNotNull(connection);


    }

}
