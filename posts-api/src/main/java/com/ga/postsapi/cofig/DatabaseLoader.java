package com.ga.postsapi.cofig;


import com.ga.postsapi.model.Post;
import com.ga.postsapi.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DatabaseLoader {

    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

    @Autowired
    private PostRepository postRepository;

    @PostConstruct
    public void init() {
        log.info("Saved {}", postRepository.save(new Post(1L, "Post1", "Post 1 text", 1L)));
        log.info("Saved {}", postRepository.save(new Post(2L, "Post2", "Post 2 text", 1L)));
        log.info("Saved {}", postRepository.save(new Post(3L, "Post3", "Post 3 text", 2L)));


    }
}