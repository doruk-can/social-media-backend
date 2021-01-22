package com.group308.socialmedia.api.v1;


import com.group308.socialmedia.core.dto.NotificationDto;
import com.group308.socialmedia.core.dto.PostInteractionDto;
import com.group308.socialmedia.core.dto.SubscriptionDto;
import com.group308.socialmedia.core.dto.common.RestResponse;
import com.group308.socialmedia.core.dto.common.Status;
import com.group308.socialmedia.core.model.domain.*;
import com.group308.socialmedia.core.model.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/interactions")
public class PostInteractionController {

    private final PostInteractionService postInteractionService;

    private final PostService postService;

    private final ContentService contentService;

    private final SubscriptionService subscriptionService;

    private final ApplicationUserService applicationUserService;

    private final OptionalNotificationService optionalNotificationService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/")
    public ResponseEntity<RestResponse<String>> interactWithPost(@RequestBody PostInteractionDto postInteractionDto) throws IOException {

        PostInteraction postInteraction = new PostInteraction();

        String postComment = postInteractionDto.getPostComment();

        if(postComment == null)
            postInteraction.setInteractionType("likeordislike");
        else
            postInteraction.setInteractionType("comment");

        long commentatorId = applicationUserService.findByUsername(postInteractionDto.getCommentatorName()).getId();

        NotificationDto notificationDto = new NotificationDto();

        String postOwnerName = postService.findById(postInteractionDto.getPostId()).getPostOwnerName();


        try {
            if(Objects.isNull(postComment)) {  // if there is a comment request shouldnt include like or dislike
                PostInteraction postInteractionCheck1 = postInteractionService.findByPostIdAndCommentatorIdAndInteractionType(   // checking if the user liked or disliked that post before
                        postInteractionDto.getPostId(), commentatorId, postInteraction.getInteractionType());
                if(postInteractionDto.getPostLike() == 1 && postInteractionDto.getPostLike() != postInteractionCheck1.getPostLike()) { // if user wants to like post which s/he disliked before
                    postInteractionService.deleteById(postInteractionCheck1.getId());
                    postInteraction.setCommentatorId(commentatorId);
                    postInteraction.setPostLike(postInteractionDto.getPostLike());
                    postInteraction.setPostId(postInteractionDto.getPostId());
                    postInteractionService.save(postInteraction);
                    notificationDto.setNotificationMessage( postInteractionDto.getCommentatorName() + " liked your post");
                    simpMessagingTemplate.convertAndSend("/topic/notification/" + postOwnerName, notificationDto);
                    //optional notification
                    Date date = new Date();
                    String str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
                    OptionalNotification optionalNotification = new OptionalNotification();
                    optionalNotification.setNotificationContent(postInteractionDto.getCommentatorName() + " liked your post!");
                    optionalNotification.setNotificationFrom(postInteractionDto.getCommentatorName());
                    optionalNotification.setNotificationTo(postOwnerName);
                    optionalNotification.setNotificationDate(str);
                    optionalNotification.setIsSent(0);
                    optionalNotificationService.save(optionalNotification);
                    //
                    return new ResponseEntity<>(RestResponse.of("User liked the post that she disliked before", Status.SUCCESS, ""), HttpStatus.OK);
                } else if (postInteractionDto.getPostLike() == 0 && postInteractionDto.getPostLike() != postInteractionCheck1.getPostLike()) {
                    postInteractionService.deleteById(postInteractionCheck1.getId());
                    postInteraction.setCommentatorId(commentatorId);
                    postInteraction.setPostDislike(postInteractionDto.getPostDislike());
                    postInteraction.setPostId(postInteractionDto.getPostId());
                    postInteractionService.save(postInteraction);
                    return new ResponseEntity<>(RestResponse.of("User disliked the post that she liked before", Status.SUCCESS, ""), HttpStatus.OK);
                }
                 if (postInteractionCheck1 != null) {
                    return new ResponseEntity<>(RestResponse.of("Users are not allowed to like or dislike same post twice", Status.SYSTEM_ERROR, ""), HttpStatus.NOT_FOUND);
                 }
            }
            throw new Exception("There is no error");
        } catch(Exception e){
            postInteraction.setCommentatorId(commentatorId);
            if(postInteraction.getInteractionType().equals("comment")) {
                postInteraction.setPostComment(postInteractionDto.getPostComment());
                notificationDto.setNotificationMessage(postInteractionDto.getCommentatorName() + " commented on your post");
                simpMessagingTemplate.convertAndSend("/topic/notification/" + postOwnerName, notificationDto);
                //optional notification
                Date date = new Date();
                String str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
                OptionalNotification optionalNotification = new OptionalNotification();
                optionalNotification.setNotificationContent(postInteractionDto.getCommentatorName() + " commented on your post: " + postInteractionDto.getPostComment());
                optionalNotification.setNotificationFrom(postInteractionDto.getCommentatorName());
                optionalNotification.setNotificationTo(postOwnerName);
                optionalNotification.setNotificationDate(str);
                optionalNotification.setIsSent(0);
                optionalNotificationService.save(optionalNotification);
                //
            }
            if(postInteraction.getInteractionType().equals("likeordislike")) {
                postInteraction.setPostLike(postInteractionDto.getPostLike());
                postInteraction.setPostDislike(postInteractionDto.getPostDislike());
                if (postInteractionDto.getPostLike() == 1) {
                    notificationDto.setNotificationMessage(postInteractionDto.getCommentatorName() + " liked your post");
                    simpMessagingTemplate.convertAndSend("/topic/notification/" + postOwnerName, notificationDto);
                    //optional notification
                    Date date = new Date();
                    String str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
                    OptionalNotification optionalNotification = new OptionalNotification();
                    optionalNotification.setNotificationContent(postInteractionDto.getCommentatorName() + " liked your post!");
                    optionalNotification.setNotificationFrom(postInteractionDto.getCommentatorName());
                    optionalNotification.setNotificationTo(postOwnerName);
                    optionalNotification.setNotificationDate(str);
                    optionalNotification.setIsSent(0);
                    optionalNotificationService.save(optionalNotification);
                    //
                }
            }
            postInteraction.setPostId(postInteractionDto.getPostId());

            postInteractionService.save(postInteraction);
            return new ResponseEntity<>(RestResponse.of("Interaction is done", Status.SUCCESS,""), HttpStatus.OK);
        }
    }


    @PostMapping("/subscribe")
    public ResponseEntity<RestResponse<String>> subscribeContent(@RequestBody SubscriptionDto subscriptionDto) {

        Subscription subscription = new Subscription();
        subscription.setSubscriberId(applicationUserService.findByUsername(subscriptionDto.getSubscriberUsername()).getId());
        Post post = postService.findById(subscriptionDto.getPostId());
        if(subscriptionDto.getSubscribedContentType().equals("topic"))
            subscription.setSubscribedContentId(contentService.findByTopic(post.getPostTopic()).getId());
        else if (subscriptionDto.getSubscribedContentType().equals("geo"))
            subscription.setSubscribedContentId(contentService.
                    findByGeoId(post.getPostGeoId()).getId());


        try { //checking if there is already that subscription
            Subscription subscriptionCheck = subscriptionService.
                    findBySubscriberIdAndSubscribedContentId(subscription.getSubscriberId(), subscription.getSubscribedContentId());
            return new ResponseEntity<>(RestResponse.of("User can't subscribe to same content twice", Status.SYSTEM_ERROR,""), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
        }

        subscriptionService.save(subscription);

        return new ResponseEntity<>(RestResponse.of("Subscribed to a content", Status.SUCCESS,""), HttpStatus.OK);
    }
}
