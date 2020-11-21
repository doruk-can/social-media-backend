package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.model.domain.PostInteraction;

import java.util.Optional;

public interface PostInteractionService extends BaseService<PostInteraction> {

    PostInteraction findByPostIdAndCommentatorIdAndInteractionType(long postId, long commentatorId, String interactionType);
}
