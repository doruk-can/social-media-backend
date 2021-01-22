package com.group308.socialmedia.api.v1;


import com.group308.socialmedia.core.dto.*;
import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.common.Status;
import com.group308.socialmedia.core.dto.user.AppUserDto;
import com.group308.socialmedia.core.model.domain.*;
import com.group308.socialmedia.core.model.domain.user.ApplicationUser;
import com.group308.socialmedia.core.model.domain.user.ApplicationUserRoleRelation;
import com.group308.socialmedia.core.model.service.*;
import com.group308.socialmedia.core.security.PasswordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/profile")
public class UserProfileController {

    private final ApplicationUserService applicationUserService;

    private final ConnectionService connectionService;

    private final SubscriptionService subscriptionService;

    private final ContentService contentService;

    private final PostService postService;

    private final PostInteractionService postInteractionService;

    private final PasswordService passwordService;

    @GetMapping("/{username}")
    public ResponseEntity<RestResponse<ProfilePageDto>> findFeedPosts(@PathVariable("username") String username) {

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


    @DeleteMapping("/{username}/delete")
    public ResponseEntity<RestResponse<String>> deleteProfile(@PathVariable("username") String username) {

        long userId = applicationUserService.findByUsername(username).getId();
        applicationUserService.deleteById(userId);

        return new ResponseEntity<>(RestResponse.of("Account is deleted", Status.SUCCESS, ""), HttpStatus.OK);
    }

    @PostMapping("/{username}/deactivate")
    public ResponseEntity<RestResponse<String>> deactivateProfile(@PathVariable("username") String username,
                                                                  @RequestParam int deactivatedDaysAmount) {

        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, deactivatedDaysAmount);
        Date currentDatePlus = c.getTime();

        ApplicationUser appUser = applicationUserService.findByUsername(username);
        applicationUserService.deleteById(appUser.getId());
        appUser.setActive(false);
        appUser.setDeactivatedUntil(currentDatePlus);
        applicationUserService.save(appUser);


        return new ResponseEntity<>(RestResponse.of("Account is deactivated", Status.SUCCESS, ""), HttpStatus.OK);
    }

    @PutMapping("/{username}/edit")
    public ResponseEntity<RestResponse<String>> editProfile(@PathVariable("username") String username,
                                                            @RequestBody AppUserDto appUserDto) throws IOException {


        final String password = passwordService.encryptPassword(appUserDto.getPassword());

        try {
            String emailUser = applicationUserService.findByEmailAndUsername(appUserDto.getEmail(), appUserDto.getUsername()).getEmail(); // if its usernames email nothing happens
        } catch (Exception e) {
            try {
                String email = applicationUserService.findByEmail(appUserDto.getEmail()).getEmail();
                return new ResponseEntity<>(RestResponse.of("User email already exists", Status.SYSTEM_ERROR, ""), HttpStatus.NOT_FOUND);

            } catch(Exception b){
                System.out.println("No error");
            }
        }

        final ApplicationUser applicationUser = applicationUserService.findByUsername(appUserDto.getUsername());

        applicationUser.setId(applicationUserService.findByUsername(username).getId());
        if(!(appUserDto.getPassword() == null || appUserDto.getPassword().equals(""))) { // checking if request contains null password
            applicationUser.setPassword(password);
        }
        if(appUserDto.getEmail() !=null && !appUserDto.getEmail().equals("")) { // checking email is empty or not in json
            applicationUser.setEmail(appUserDto.getEmail());
        }
        if(appUserDto.getProfilePicture() != null && !appUserDto.getProfilePicture().equals("")) {
            applicationUser.setProfilePicture(appUserDto.getProfilePicture());
        }


        applicationUserService.save(applicationUser);


        return new ResponseEntity<>(RestResponse.of("Profile is updated", Status.SUCCESS,""), HttpStatus.OK);
    }

    @DeleteMapping("/{username}/removeConnection/{removedUsername}")
    public ResponseEntity<RestResponse<String>> deleteConnection(@PathVariable("username") String username,
                                                                 @PathVariable("removedUsername") String deletedUsername) {
        long userId = applicationUserService.findByUsername(username).getId();
        long deletedUserId = applicationUserService.findByUsername(deletedUsername).getId();

        long deletedConnectionId = connectionService.findByFollowerIdAndFollowingId(userId, deletedUserId).getId();
        connectionService.deleteById(deletedConnectionId);
        return new ResponseEntity<>(RestResponse.of("User is unfollowed", Status.SUCCESS,""), HttpStatus.OK);
    }

    @DeleteMapping("/{username}/unsubscribeTopic/{unsubscribedTopicName}")
    public ResponseEntity<RestResponse<String>> deleteTopic(@PathVariable("username") String username,
                                                                 @PathVariable("unsubscribedTopicName") String deletedTopicName) {
        deletedTopicName = '#' + deletedTopicName;  // spring doesnt accept # in path variable. Here # character is added.
        long userId = applicationUserService.findByUsername(username).getId();
        long topicId = contentService.findByTopic(deletedTopicName).getId();

        long deletedSubscriptionId = subscriptionService.findBySubscriberIdAndSubscribedContentId(userId, topicId).getId();

        subscriptionService.deleteById(deletedSubscriptionId);
        return new ResponseEntity<>(RestResponse.of("Unsubscription from the topic is completed", Status.SUCCESS,""), HttpStatus.OK);
    }

    @DeleteMapping("/{username}/unsubscribeLocation/{unsubscribedLocationId}")
    public ResponseEntity<RestResponse<String>> deleteLocation(@PathVariable("username") String username,
                                                               @PathVariable("unsubscribedLocationId") String deletedLocationId) {
        long userId = applicationUserService.findByUsername(username).getId();
        long locationId = contentService.findByGeoId(deletedLocationId).getId();

        long deletedSubscriptionId = subscriptionService.findBySubscriberIdAndSubscribedContentId(userId, locationId).getId();

        subscriptionService.deleteById(deletedSubscriptionId);
        return new ResponseEntity<>(RestResponse.of("Unsubscription from the location is completed", Status.SUCCESS,""), HttpStatus.OK);
    }

    class IdWithCount{
        long id;
        long count;

        public long getCount() {
            return count;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setCount(long count) {
            this.count = count;
        }
    }


    @GetMapping("/{username}/recommendations")
    public ResponseEntity<RestResponse<List<RecommendationDto>>> findRecommendations(@PathVariable("username") String username) {

        long userId = applicationUserService.findByUsername(username).getId();
        List<Connection> followingUsers = connectionService.findAllByFollowerId(userId);
        List<Long> followingIds = new ArrayList<>();
        for(int i=0; i< followingUsers.size(); i++) { // finding users which are followed by the user
            followingIds.add(followingUsers.get(i).getFollowingId());
        }

        Set<Long> allIds = new HashSet<>();
        boolean isThatOkayToAdd = false;
        for(int i=0; i < followingIds.size(); i++) {
            List<Connection> followingUsers2 = connectionService.findAllByFollowerId(followingIds.get(i));
            List<Long> followingIds2 = new ArrayList<>();
            for(int j=0; j< followingUsers2.size(); j++) {
                followingIds2.add(followingUsers2.get(j).getFollowingId());
                isThatOkayToAdd = true;
            }
            if(isThatOkayToAdd)
                allIds.addAll(followingIds2);
            isThatOkayToAdd = false;
        }

        List<IdWithCount> idWithCounts = new ArrayList<>();
        List<Long> allIdsList = new ArrayList<>();
        allIdsList.addAll(allIds);


        for(int i=0; i < allIdsList.size(); i++) {  // user's followings' followings
            IdWithCount idWithCount = new IdWithCount();
            idWithCount.setId(allIdsList.get(i));
            idWithCount.setCount(0);
            for(int j=0; j<followingIds.size(); j++) {  // user's followings
                try{
                    connectionService.findByFollowerIdAndFollowingId(followingIds.get(j), allIdsList.get(i)); // looking if potential recommended user is followed by user's connection
                    idWithCount.setCount(idWithCount.getCount() + 1); // if true recommendation count increases
                } catch (Exception e) {

                }
            }
            idWithCounts.add(idWithCount);
        }


        idWithCounts.sort(Comparator.comparing(IdWithCount::getCount).reversed());



        List<RecommendationDto> recommendationDtos = new ArrayList<>();
        for(int i=0; i < idWithCounts.size(); i++) {
            RecommendationDto recommendationDto = new RecommendationDto();
            recommendationDto.setUserId(idWithCounts.get(i).getId());
            recommendationDto.setRecommendationCount(idWithCounts.get(i).getCount());
            String uname = applicationUserService.findById(recommendationDto.getUserId()).getUsername();
            String profilePic = applicationUserService.findById(recommendationDto.getUserId()).getProfilePicture();
            recommendationDto.setUsername(uname);
            recommendationDto.setProfileImage(profilePic);
            recommendationDtos.add(recommendationDto);
            if(i==3)
                break;  // returning 4 is enough
        }


        System.out.println(recommendationDtos);


        return new ResponseEntity<>(RestResponse.of(recommendationDtos, Status.SUCCESS, ""), HttpStatus.OK);
    }



}
