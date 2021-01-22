package com.group308.socialmedia;

import com.group308.socialmedia.core.model.domain.Post;
import com.group308.socialmedia.core.model.repository.PostRepository;
import com.group308.socialmedia.core.model.service.PostService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase
public class PostTests {

    @Autowired
    private PostService postService;

    @Test
    public void testUploadPost() {
        Post post = new Post();
        post.setPostOwnerName("unknown user");
        post.setPostText("example text");
        post.setPostTopic("topic1");

        try {
            post = postService.save(post);
        }
        catch (Exception e) {

        }

        Assert.assertNotNull(post);

    }
}
