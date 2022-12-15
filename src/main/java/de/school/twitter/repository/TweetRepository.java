package de.school.twitter.repository;

import de.school.twitter.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {

}
