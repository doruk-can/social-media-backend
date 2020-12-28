package com.group308.socialmedia;


import com.group308.socialmedia.api.v1.FeedController;
import com.group308.socialmedia.api.v1.PostController;
import com.group308.socialmedia.core.model.domain.Post;
import com.group308.socialmedia.core.model.repository.PostRepository;
import com.group308.socialmedia.core.model.service.PostService;
import com.group308.socialmedia.core.security.PasswordService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTests {


    @MockBean
    private PostRepository postRepository;

    @Autowired
    @MockBean
    private PostService postService;


    String postOwner;

    @Before
    public void setup(){
        postOwner = "ali";
    }

    @Test
    public void test11() {
        Post post = new Post();
        post.setPostText("its a post");
        post.setPostOwnerName("ali");
        post = postService.save(post);
        Assert.assertNotNull(postService.findAllByPostOwnerName(postOwner));
    }

}
