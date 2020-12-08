package com.group308.socialmedia.api.v1;


import com.group308.socialmedia.core.dto.*;
import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.common.Status;
import com.group308.socialmedia.core.model.domain.*;
import com.group308.socialmedia.core.model.repository.PostInteractionRepository;
import com.group308.socialmedia.core.model.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

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

    private final ApplicationUserService applicationUserService;

    private final ReportedContentService reportedContentService;

    private final PostMapper postMapper;

    private JavaMailSender javaMailSender;


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
            long commentatorId = applicationUserService.findByUsername(username).getId();

            try {  // finding if user liked that post or not to show blue like or dislike button
                if (postInteractionService.findByPostIdAndCommentatorIdAndInteractionType(feedPosts.get(i).getId(), commentatorId
                        , "likeordislike").getPostLike() == 1)
                    tempFeedPostWithInteractionDto.setUserLikedIt(true);
            } catch (Exception e) {
            }
            try {
                if (postInteractionService.findByPostIdAndCommentatorIdAndInteractionType(feedPosts.get(i).getId(), commentatorId
                        , "likeordislike").getPostDislike() == 1)
                    tempFeedPostWithInteractionDto.setUserDislikedIt(true);
            } catch (Exception e) {

            }


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


        return new ResponseEntity<>(RestResponse.of(feedPostsWithInteraction, Status.SUCCESS, ""), HttpStatus.OK);
    }


    @GetMapping("/{username}/{postId}")
    public ResponseEntity<RestResponse<FeedDto>> findSpecificFeedPost(@PathVariable("username") String username,
                                                                      @PathVariable("postId") long postId) {

        final Post feedPost = postService.findById(postId);

        FeedDto feedPostWithInteractionDto = new FeedDto();

        feedPostWithInteractionDto.setPostGeoId(feedPost.getPostGeoId());
        feedPostWithInteractionDto.setPostGeoName(feedPost.getPostGeoName());
        feedPostWithInteractionDto.setPostId(feedPost.getId());
        feedPostWithInteractionDto.setPostImage(feedPost.getPostImage());
        feedPostWithInteractionDto.setPostOwnerName(feedPost.getPostOwnerName());
        feedPostWithInteractionDto.setPostText(feedPost.getPostText());
        feedPostWithInteractionDto.setPostTopic(feedPost.getPostTopic());
        feedPostWithInteractionDto.setPostVideoURL(feedPost.getPostVideoURL());
        feedPostWithInteractionDto.setPostDate(feedPost.getCreatedDate());// adding date to sort posts

        long commentatorId = applicationUserService.findByUsername(username).getId();

        try {  // finding if user liked that post or not to show blue like or dislike button
            if (postInteractionService.findByPostIdAndCommentatorIdAndInteractionType(feedPost.getId(), commentatorId
                    , "likeordislike").getPostLike() == 1)
                feedPostWithInteractionDto.setUserLikedIt(true);
        } catch (Exception e) {
        }
        try {
            if (postInteractionService.findByPostIdAndCommentatorIdAndInteractionType(feedPost.getId(), commentatorId
                    , "likeordislike").getPostDislike() == 1)
                feedPostWithInteractionDto.setUserDislikedIt(true);
        } catch (Exception e) {

        }

        try { // if there is no like or dislike summing them up could cause error
            long totalLike = postInteractionService.sumPostLike(feedPost.getId());
            long totalDislike = postInteractionService.sumPostDislike(feedPost.getId());

            feedPostWithInteractionDto.setTotalPostLike(totalLike); // setting total like
            feedPostWithInteractionDto.setTotalPostDislike(totalDislike);
        }
        catch (Exception e) {

        }

        //finding comments of the post
        List<PostInteraction> postInteractionList = postInteractionService.findAllByPostId(feedPost.getId());
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

        feedPostWithInteractionDto.setPostCommentDto(postCommentDtoList);



        return new ResponseEntity<>(RestResponse.of(feedPostWithInteractionDto, Status.SUCCESS, ""), HttpStatus.OK);
    }







    @PostMapping("/{username}/report/{reportedPostId}")
    public ResponseEntity<RestResponse<String>> reportPost(@PathVariable("username") String username,
                                                                     @PathVariable("reportedPostId") long reportedPostId) {
        ReportedContent reportedContent = new ReportedContent();
        reportedContent.setReportType("post");
        reportedContent.setReportedPostId(reportedPostId);

        long userId = applicationUserService.findByUsername(username).getId();
        reportedContent.setReporterId(userId);

            if(reportedContentService.findByReporterIdAndReportedPostId(userId, reportedPostId) == null) //checking if the post already reported
                reportedContentService.save(reportedContent);
            else
                return new ResponseEntity<>(RestResponse.of("Post is already reported", Status.SYSTEM_ERROR,""), HttpStatus.NOT_FOUND);

        sendEmail("drukcan@gmail.com");

        return new ResponseEntity<>(RestResponse.of("Post is reported", Status.SUCCESS,""), HttpStatus.OK);
    }

    void sendEmail(String mailTo) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mailTo);

        msg.setSubject("Report cs308");
        msg.setText("New post is reported. You may check them in app.");

        javaMailSender.send(msg);

    }

}




