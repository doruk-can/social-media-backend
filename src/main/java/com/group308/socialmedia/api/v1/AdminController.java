package com.group308.socialmedia.api.v1;


import com.group308.socialmedia.core.dto.FeedDto;
import com.group308.socialmedia.core.dto.PostCommentDto;
import com.group308.socialmedia.core.dto.ProfilePageDto;
import com.group308.socialmedia.core.dto.RequestedUserDto;
import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.common.Status;
import com.group308.socialmedia.core.model.domain.*;
import com.group308.socialmedia.core.model.domain.user.ApplicationUser;
import com.group308.socialmedia.core.model.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/admin")
public class AdminController {

    private final ApplicationUserService applicationUserService;

    private final ConnectionService connectionService;

    private final SubscriptionService subscriptionService;

    private final ContentService contentService;

    private final PostService postService;

    private final PostInteractionService postInteractionService;

    @GetMapping("/search/{username}")
    public ResponseEntity<RestResponse<List<RequestedUserDto>>> findFeedPosts(@PathVariable("username") String username) {

        List<ApplicationUser> requestedUsers = applicationUserService.findAllByUsernameContains(username);
        List<RequestedUserDto> requestedUserDtos = new ArrayList<>();


        for( int i=0; i< requestedUsers.size(); i++) {
            RequestedUserDto requestedUserDto = new RequestedUserDto(); // ?disardeyken hata veriyor
            requestedUserDto.setUserId(requestedUsers.get(i).getId());
            requestedUserDto.setUsername(requestedUsers.get(i).getUsername());
            requestedUserDtos.add(requestedUserDto);
        }

        return new ResponseEntity<>(RestResponse.of(requestedUserDtos, Status.SUCCESS, ""), HttpStatus.OK);
    }

    @GetMapping("/search/{username}/profile")
    public ResponseEntity<RestResponse<ProfilePageDto>> getUserProfile(@PathVariable("username") String username) {

        ApplicationUser applicationUser = applicationUserService.findByUsername(username);
        ProfilePageDto profilePageDto = new ProfilePageDto();
        profilePageDto.setUsername(applicationUser.getUsername());
        profilePageDto.setEmail(applicationUser.getEmail());
        profilePageDto.setProfilePicture(applicationUser.getProfilePicture());
        profilePageDto.setActive(applicationUser.isActive());
        //appUserDto.setDeleted(applicationUser.isDeleted());
        Date deactivatedUntil = applicationUser.getDeactivatedUntil();
        profilePageDto.setDeactivatedUntil(deactivatedUntil);

        //finding user's followers
        long userId = applicationUser.getId();
        List<Connection> followingUsers = connectionService.findAllByFollowingId(userId);
        String uname;
        List<String> followingUsernames = new ArrayList<>();
        for(int i=0; i< followingUsers.size(); i++) {
            uname = applicationUserService.findById(followingUsers.get(i).getFollowerId()).getUsername();
            followingUsernames.add(uname);
        }
        profilePageDto.setFollowerNamesList(followingUsernames);

        //finding user's following users
        List<Connection> followers = connectionService.findAllByFollowerId(userId);
        List<String> followerNames = new ArrayList<>();
        for(int i=0; i< followers.size(); i++) {
            uname = applicationUserService.findById(followers.get(i).getFollowingId()).getUsername();
            followerNames.add(uname);
        }
        profilePageDto.setFollowingNamesList(followerNames);

        // adding subscribed topics and locations
        List<Subscription> subscriptionList = subscriptionService.findAllBySubscriberId(userId);
        List<Long> contentIdList = new ArrayList<>();  // finding subscribed posts, firstly finding content ids
        for(int i=0; i< subscriptionList.size(); i++) {
            contentIdList.add(subscriptionList.get(i).getSubscribedContentId());
        }

        List<String> subscribedTopicNamesList = new ArrayList<>();
        List<String> subscribedLocationIdsList = new ArrayList<>();

        List<Content> contentList = new ArrayList<>();
        for(int i=0; i< contentIdList.size(); i++) {
            contentList.add(contentService.findById(contentIdList.get(i)));
            if(contentList.get(i).getContentType().equals("geo"))
                subscribedLocationIdsList.add(contentList.get(i).getGeoId());
            else if(contentList.get(i).getContentType().equals("topic"))
                subscribedTopicNamesList.add(contentList.get(i).getTopic());
        }

        profilePageDto.setSubscribedLocationIdsList(subscribedLocationIdsList);
        profilePageDto.setSubscribedTopicNamesList(subscribedTopicNamesList);

        // posts created before and their interactions
        final List<Post> feedPosts = postService.findAllByPostOwnerName(username);
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
            List<PostCommentDto> postCommentDtoList = new ArrayList<>();
            for(int j=0; j< postInteractionList.size(); j++) {
                PostCommentDto tempPostCommentDto = new PostCommentDto();
                tempPostCommentDto.setPostComment(postInteractionList.get(j).getPostComment());
                String commentatorUsername = applicationUserService.findById(postInteractionList.get(j).getCommentatorId()).getUsername();
                tempPostCommentDto.setCommentatorName(commentatorUsername);
                if (tempPostCommentDto.getPostComment() == null)
                    continue;
                postCommentDtoList.add(tempPostCommentDto);
            }

            tempFeedPostWithInteractionDto.setPostCommentDto(postCommentDtoList);

            feedPostsWithInteraction.add(tempFeedPostWithInteractionDto);

        }

        feedPostsWithInteraction.sort(Comparator.comparing(FeedDto::getPostDate).reversed()); //sorting posts


        profilePageDto.setUserPostsList(feedPostsWithInteraction);


        return new ResponseEntity<>(RestResponse.of(profilePageDto, Status.SUCCESS, ""), HttpStatus.OK);
    }






    }
