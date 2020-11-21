package com.group308.socialmedia.core.model.repository;

import com.group308.socialmedia.core.model.domain.PostInteraction;

import java.util.Optional;

public interface PostInteractionRepository extends BaseRepository<PostInteraction, Long> {

    Optional<PostInteraction> findByPostIdAndCommentatorIdAndInteractionType(long postId, long commentatorId, String interactionType);

}
