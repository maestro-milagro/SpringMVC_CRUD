package org.example.repository;

import org.example.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostRepository {
  Map<Long,Post> all();

  Optional<Post> getById(long id);

  Post save(Post post);

  void removeById(long id);
}
