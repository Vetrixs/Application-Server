package de.school.twitter.controller;

import de.school.twitter.model.Tweet;
import de.school.twitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Tweet getTweetById(@PathVariable("id") Integer aId) {
        return tweetRepository.getReferenceById(aId);
    }

    //TODO: not working: Cannot delete or update a parent row: a foreign key constraint fails
    @DeleteMapping("/tweet/{id}")
    public void deleteTweetById(@PathVariable("id") Integer aId) {
        tweetRepository.deleteById(aId);
    }

    @GetMapping("/tweet/{id}/like")
    public void liketweet(@PathVariable("id") Integer aId) {
        Tweet tweet = tweetRepository.getReferenceById(aId);
        tweet.setLikes(tweet.getLikes() + 1);
        tweetRepository.save(tweet);
    }

}
