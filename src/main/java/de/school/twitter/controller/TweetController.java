package de.school.twitter.controller;

import java.util.List;

import de.school.twitter.model.Tweet;
import de.school.twitter.repository.TweetRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TweetController {

    @Autowired
    TweetRepository tweetRepository;

    @GetMapping("/")
    public List<Tweet> getAllTweets(){
        return tweetRepository.findAll();
    }

    @PostMapping("/tweet")
    public void saveTweet(@RequestBody Tweet aTweet){
        Tweet newTweet = new Tweet();
        newTweet.setContent(aTweet.getContent());
        newTweet.setLikes(aTweet.getLikes());
        tweetRepository.save(newTweet);
    }

    @GetMapping("/tweet/{id}")
    public Tweet getTweetById(@PathParam("id") Integer aId){
        return tweetRepository.getReferenceById(aId);
    }

    @DeleteMapping("/tweet/{id}")
    public Tweet deleteTweetById(@PathParam("id") Integer aId){
        return tweetRepository.getReferenceById(aId);
    }
}
