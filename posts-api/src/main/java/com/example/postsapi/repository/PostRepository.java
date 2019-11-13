package com.example.postsapi.repository;

import com.example.postsapi.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class PostRepository implements CrudRepository<Post, Long> {

    @Override
    public <S extends Post> S save(S s) {
        return null;
    }

    @Override
    public <S extends Post> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Post> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Post> findAll() {
        return null;
    }

    @Override
    public Iterable<Post> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Post post) {

    }

    @Override
    public void deleteAll(Iterable<? extends Post> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
