package com.group308.socialmedia.api.v1;


import com.group308.socialmedia.core.dto.FeedDto;
import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.common.Status;
import com.group308.socialmedia.core.model.domain.Post;
import com.group308.socialmedia.core.model.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/feed")
public class FeedController {

    private final PostInteractionService postInteractionService;

    private final PostService postService;

    private final ContentService contentService;

    private final SubscriptionService subscriptionService;

    private final ApplicationUserService applicationUserService;

    /*@GetMapping("/{username}")
    public ResponseEntity<RestResponse<List<FeedDto>>> findFeedPosts(@PathVariable("username") String username) {

        long userId = applicationUserService.findByUsername(username).getId();

        List<Post> posts = (List<Post>) postService.findAllByPostOwnerName(username);


        List<FeedDto> feedDtos;

        FeedDto feedDto = new FeedDto();






        //final List<ProdGroup> prodGroups = prodGroupService.findAllByParentGroupIdAndActiveTrue(ROOT_PARENT_GROUP_ID);
        final List<ProdGroupDto> prodGroupDtos = prodGroupMapper.asDtos(prodGroups);

        return new ResponseEntity<>(RestResponse.of(feedDtos, Status.SUCCESS, ""), HttpStatus.OK);

    }*/

}
