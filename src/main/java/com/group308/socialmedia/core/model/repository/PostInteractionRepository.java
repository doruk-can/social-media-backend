package com.group308.socialmedia.core.model.repository;

import com.group308.socialmedia.core.model.domain.PostInteraction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostInteractionRepository extends BaseRepository<PostInteraction, Long> {

    Optional<PostInteraction> findByPostIdAndCommentatorIdAndInteractionType(long postId, long commentatorId, String interactionType);

    List<PostInteraction> findAllByPostId(long postId);

   // List<PostInteraction> findAllByCommentatorIdAndPostLike(long commentatorId, long postLike);
  // @Query(value = "SELECT * from PostInteraction postInteraction where postInteraction.commentatorId = :commentatorId and postInteraction.postLike = 1", nativeQuery = true)
  // List<PostInteraction> findAllByCommentatorIdAndPostLike(@Param("commentatorId") long commentatorId);

   // List<PostInteraction> findAllByCommentatorIdAndPostDislike(long commentatorId, long postDislike);
  // @Query(value = "SELECT * from PostInteraction postInteraction where postInteraction.commentatorId = :commentatorId and postInteraction.postDislike = 1", nativeQuery = true)
   //List<PostInteraction> findAllByCommentatorIdAndPostDislike(@Param("commentatorId") long commentatorId);


    @Query("SELECT sum(postInteraction.postLike) from PostInteraction postInteraction where postInteraction.postId = :postId")
    long sumPostLike(@Param("postId") long postId);

    @Query("SELECT sum(postInteraction.postDislike) from PostInteraction postInteraction where postInteraction.postId = :postId")
    long sumPostDislike(@Param("postId") long postId);
}
