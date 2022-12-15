package de.school.twitter.repository;

import java.util.List;

import de.school.twitter.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "SELECT * FROM comments WHERE tweet_id_id = :id", nativeQuery = true)
    List<Comment> getAllCommentsByTweet(@Param("id") int aId);

}
