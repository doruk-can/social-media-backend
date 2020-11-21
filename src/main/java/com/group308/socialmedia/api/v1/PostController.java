package com.group308.socialmedia.api.v1;

import com.group308.socialmedia.core.dto.PostDto;
import com.group308.socialmedia.core.dto.PostMapper;
import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.common.Status;
import com.group308.socialmedia.core.model.domain.Content;
import com.group308.socialmedia.core.model.domain.Post;
import com.group308.socialmedia.core.model.service.ContentService;
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

    private final ContentService contentService;

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
        postService.save(post);

        //updating content table
        Content content1 = new Content();
        Content content2 = new Content();
        if(postDto.getPostTopic() != null && postDto.getPostTopic() != "") { // setting content type topic or geo. it is to prevent
            content1.setContentType("topic");                                // duplicate topics or locations in content table
            content1.setTopic(postDto.getPostTopic());
            try {
                contentService.findByTopic(content1.getTopic());
            } catch(Exception e) {
                contentService.save(content1);
            }
        }
        if (postDto.getPostLocationLatitude() != 0) {
            content2.setContentType("geo");
            content2.setGeoLatitude(postDto.getPostLocationLatitude());
            content2.setGeoLongitude(postDto.getPostLocationLongitude());
            try {
                contentService.findByGeoLatitudeAndGeoLongitude(content2.getGeoLatitude(), content2.getGeoLongitude());
            } catch (Exception e) {
                contentService.save(content2);
            }
        }


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