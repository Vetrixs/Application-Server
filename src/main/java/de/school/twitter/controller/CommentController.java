package de.school.twitter.controller;

import java.util.List;

import de.school.twitter.model.Comment;
import de.school.twitter.model.Tweet;
import de.school.twitter.repository.CommentRepository;
import de.school.twitter.repository.TweetRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TweetRepository tweetRepository;

    @GetMapping("/comments")
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    @PostMapping("tweet/{id}/comment")
    public void addComment(
            @PathVariable("id") Integer aId,
            @RequestBody Comment aComment){
        Comment comment = new Comment();
        Tweet tweet = tweetRepository.getReferenceById(aId);
        comment.setTweetId(tweet);
        comment.setContent(aComment.getContent());
        commentRepository.save(comment);
    }

    @GetMapping("/tweet/{id}/comments")
    public List<Comment> getAllCommentsForTweet(@PathVariable("id") Integer aId){
        return commentRepository.getAllCommentsByTweet(aId);
    }
}
