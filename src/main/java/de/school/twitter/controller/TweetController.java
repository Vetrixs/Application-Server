package de.school.twitter.controller;

import java.util.List;

import de.school.twitter.model.Tweet;
import de.school.twitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public Tweet getTweetById(@PathVariable("id") Integer aId){
            return tweetRepository.getReferenceById(aId);
    }

    //TODO: not working: Cannot delete or update a parent row: a foreign key constraint fails
    @DeleteMapping("/tweet/{id}")
    public void deleteTweetById(@PathVariable("id") Integer aId){
        tweetRepository.deleteById(aId);
    }
}
