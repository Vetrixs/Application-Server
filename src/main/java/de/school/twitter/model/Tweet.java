package de.school.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tweets")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;

    private int likes;

}
