package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.model.domain.PostInteraction;

import java.util.List;
import java.util.Optional;

public interface PostInteractionService extends BaseService<PostInteraction> {

    PostInteraction findByPostIdAndCommentatorIdAndInteractionType(long postId, long commentatorId, String interactionType);

    List<PostInteraction> findAllByPostId(long postId);

    //List<PostInteraction> findAllByCommentatorIdAndPostLike(long commentatorId, long postLike);
    //List<PostInteraction> findAllByCommentatorIdAndPostLike(long commentatorId);

    //List<PostInteraction> findAllByCommentatorIdAndPostDislike(long commentatorId, long postDislike);
    //List<PostInteraction> findAllByCommentatorIdAndPostDislike(long commentatorId);

    long sumPostLike(long postId);

    long sumPostDislike(long postId);

}
