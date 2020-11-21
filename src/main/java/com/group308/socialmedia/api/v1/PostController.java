package com.group308.socialmedia.api.v1;

import com.group308.socialmedia.core.dto.PostDto;
import com.group308.socialmedia.core.dto.PostMapper;
import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.common.Status;
import com.group308.socialmedia.core.model.domain.Post;
import com.group308.socialmedia.core.model.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class PostController {

    private final PostService postService;

    private final PostMapper postMapper;

    @PostMapping("/upload")
    public ResponseEntity<RestResponse<String>> uploadPost(@RequestBody PostDto postDto) throws IOException {


        String pText = postDto.getPostText();               // parsing the topic from the text
        int beginIndex = pText.indexOf('#');
        if(beginIndex != -1) {
            int endIndex = pText.indexOf(' ', beginIndex);
            if (endIndex == -1)
                endIndex = pText.length();
            String pTopic = pText.substring(beginIndex, endIndex);
            if(pTopic.length() == 1)
                pTopic = "";
            postDto.setPostTopic(pTopic);
        }

        final Post post = postMapper.asEntity(postDto);

      //  post.setPostTopic();

        postService.save(post);

        return new ResponseEntity<>(RestResponse.of("Post is uploaded", Status.SUCCESS,""), HttpStatus.OK);
    }

    @PutMapping("/edit/{postId}")
    public ResponseEntity<RestResponse<String>> editPost(@PathVariable(value = "postId") Long postId, @RequestBody PostDto postDto) throws IOException {

        postService.update(postId, postDto);

        return new ResponseEntity<>(RestResponse.of("Post is updated", Status.SUCCESS,""), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<RestResponse<String>> deletePost(@PathVariable(value = "postId") Long postId) throws IOException {

        postService.deleteById(postId);

        return new ResponseEntity<>(RestResponse.of("Post is deleted", Status.SUCCESS,""), HttpStatus.OK);
    }


}