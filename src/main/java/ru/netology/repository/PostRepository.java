package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
  private final ConcurrentMap<Long, Post> allPosts = new ConcurrentHashMap<>();
  private static AtomicLong counter = new AtomicLong(0);

  public List<Post> all() {
    return new ArrayList<Post>(allPosts.values());
  }

  public Optional<Post> getById(long id) {
    if (allPosts.containsKey(id)) {
      return Optional.of(allPosts.get(id));
    }
    throw new NotFoundException();
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      counter.getAndIncrement();
      post.setId(counter.get());
      allPosts.put(post.getId(), post);
      return post;
    } else if (post.getId() >= 1) {
      if (allPosts.containsKey(post.getId())) {
        allPosts.get(post.getId()).setContent(post.getContent());
        return post;
      }
      throw new NotFoundException();
    }
    return null;
  }

  public void removeById(long id) {
    if (allPosts.containsKey(id)) {
      allPosts.remove(id);
    } else {
      System.out.println("404 ID " + id + " not found");
    }
  }
}