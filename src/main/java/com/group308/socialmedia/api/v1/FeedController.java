package com.group308.socialmedia.api.v1;


import com.group308.socialmedia.core.dto.FeedDto;
import com.group308.socialmedia.core.dto.PostDto;
import com.group308.socialmedia.core.dto.PostInteractionDto;
import com.group308.socialmedia.core.dto.PostMapper;
import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.common.Status;
import com.group308.socialmedia.core.model.domain.*;
import com.group308.socialmedia.core.model.repository.PostInteractionRepository;
import com.group308.socialmedia.core.model.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/feed")
public class FeedController {

    private final PostInteractionService postInteractionService;

    private final PostService postService;

    private final ContentService contentService;

    private final ConnectionService connectionService;

    private final SubscriptionService subscriptionService;

    private final PostInteractionRepository postInteractionRepository; // silinecek

    private final ApplicationUserService applicationUserService;

    private final PostMapper postMapper;

    @GetMapping("/{username}")
    public ResponseEntity<RestResponse<List<FeedDto>>> findFeedPosts(@PathVariable("username") String username) {

        long userId = applicationUserService.findByUsername(username).getId();

        List<Connection> connectionList = connectionService.findAllByFollowerId(userId);
        List<Long> followingIdList = new ArrayList<>();
        List<String> followingNameList = new ArrayList<>();
        for(int i=0; i< connectionList.size(); i++) {   // finding connections' posts
            followingIdList.add(connectionList.get(i).getFollowingId()); // taking following ids
            followingNameList.add(applicationUserService.findById(followingIdList.get(i)).getUsername()); // finding username by id and adding to the list
        }
        Set<Post> postSet1 = new HashSet<>();
        for(int i=0; i< followingNameList.size(); i++) {
            postSet1.addAll(postService.findAllByPostOwnerName(followingNameList.get(i)));
        }

        List<Subscription> subscriptionList = subscriptionService.findAllBySubscriberId(userId);
        List<Long> contentIdList = new ArrayList<>();  // finding subscribed posts, firstly finding content ids
        for(int i=0; i< subscriptionList.size(); i++) {
            contentIdList.add(subscriptionList.get(i).getSubscribedContentId());
        }

        List<Content> contentList = new ArrayList<>();
        List<String> contentIdNameList = new ArrayList<>();
        for(int i=0; i< contentIdList.size(); i++) {
            contentList.add(contentService.findById(contentIdList.get(i)));
            if(contentList.get(i).getContentType().equals("geo"))
                contentIdNameList.add(contentList.get(i).getGeoId());
            else if(contentList.get(i).getContentType().equals("topic"))
                contentIdNameList.add(contentList.get(i).getTopic());
        }

        Set<Post> postSet2 = new HashSet<>();
        for(int i=0; i< contentIdNameList.size(); i++) {
            postSet2.addAll(postService.findAllByPostGeoIdOrPostTopic(contentIdNameList.get(i), contentIdNameList.get(i)));
        }

        postSet1.addAll(postSet2);  // combining connections' posts and subscriptions' posts

        final List<Post> feedPosts = new ArrayList<>(postSet1);

        List<FeedDto> feedPostsWithInteraction = new ArrayList<>();
        for(int i=0; i< feedPosts.size(); i++) {
            FeedDto tempFeedPostWithInteractionDto = new FeedDto();
            tempFeedPostWithInteractionDto.setPostGeoId(feedPosts.get(i).getPostGeoId());
            tempFeedPostWithInteractionDto.setPostGeoName(feedPosts.get(i).getPostGeoName());
            tempFeedPostWithInteractionDto.setPostId(feedPosts.get(i).getId());
            tempFeedPostWithInteractionDto.setPostImage(feedPosts.get(i).getPostImage());
            tempFeedPostWithInteractionDto.setPostOwnerName(feedPosts.get(i).getPostOwnerName());
            tempFeedPostWithInteractionDto.setPostText(feedPosts.get(i).getPostText());
            tempFeedPostWithInteractionDto.setPostTopic(feedPosts.get(i).getPostTopic());
            tempFeedPostWithInteractionDto.setPostVideoURL(feedPosts.get(i).getPostVideoURL());
            tempFeedPostWithInteractionDto.setPostDate(feedPosts.get(i).getCreatedDate());// adding date to sort posts

            try { // if there is no like or dislike summing them up could cause error
                long totalLike = postInteractionService.sumPostLike(feedPosts.get(i).getId());
                long totalDislike = postInteractionService.sumPostDislike(feedPosts.get(i).getId());

                tempFeedPostWithInteractionDto.setTotalPostLike(totalLike); // setting total like
                tempFeedPostWithInteractionDto.setTotalPostDislike(totalDislike);
            }
            catch (Exception e) {

            }

            //finding comments of the post
            List<PostInteraction> postInteractionList = postInteractionService.findAllByPostId(feedPosts.get(i).getId());
            List<PostInteractionDto> postInteractionDtoList = new ArrayList<>();
            for(int j=0; j< postInteractionList.size(); j++) {
                PostInteractionDto tempPostInteractionDto = new PostInteractionDto();
                tempPostInteractionDto.setPostComment(postInteractionList.get(j).getPostComment());
                tempPostInteractionDto.setCommentatorId(postInteractionList.get(j).getCommentatorId());
                if (tempPostInteractionDto.getPostComment() == null)
                    continue;
                postInteractionDtoList.add(tempPostInteractionDto);
            }

            tempFeedPostWithInteractionDto.setPostInteractionDto(postInteractionDtoList);

            feedPostsWithInteraction.add(tempFeedPostWithInteractionDto);

        }

        feedPostsWithInteraction.sort(Comparator.comparing(FeedDto::getPostDate).reversed()); //sorting posts


        return new ResponseEntity<>(RestResponse.of(feedPostsWithInteraction, Status.SUCCESS, ""), HttpStatus.OK);
    }

}
