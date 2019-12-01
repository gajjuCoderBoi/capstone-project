package com.ga.commentsapi.config;

import com.ga.commentsapi.model.Comment;
import com.ga.commentsapi.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/*************************************************************************
 *
 *      The DatabaseLoader creates some initial records in the commentRepository
 *      to facilitate development
 *
 *************************************************************************/


@Service
public class DatabaseLoader {

    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

    @Autowired
    private CommentRepository commentRepository;

    @PostConstruct
    public void init() {
        // String text, Long postId, Long userId, User user
        log.info("Saved {}", commentRepository.save(new Comment("Comment on post 1 with user 1", 1L, 1L)));
        log.info("Saved {}", commentRepository.save(new Comment("Comment on post 1 with user 2", 1L, 2L)));

        log.info("Saved {}", commentRepository.save(new Comment("Comment on post 2 with user 2", 2L, 2L)));
        log.info("Saved {}", commentRepository.save(new Comment("Comment on post 2 with user 1", 2L, 1L)));

    }
}