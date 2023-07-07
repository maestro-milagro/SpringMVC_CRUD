package org.example.repository;

import org.example.exception.NotFoundException;
import org.springframework.stereotype.Repository;
import org.example.model.Post;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
    protected Map<Long, Post> postCollection = Collections.synchronizedMap(new HashMap<>());
    protected Map<Long, Post> removedPostCollection = Collections.synchronizedMap(new HashMap<>());
    protected AtomicLong idCount = new AtomicLong(0);

    public Map<Long, Post> all() {
        return postCollection;
    }

    public Optional<Post> getById(long id) {
        if(Optional.ofNullable(postCollection.get(id)).isPresent()) {
            return Optional.ofNullable(postCollection.get(id));
        } else {throw new NotFoundException("Выбран несуществующий или удалённый пост");
        }
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(idCount.getAndIncrement());
            postCollection.put(post.getId(), post);
        } else if (getById(post.getId()).isPresent()) {
            postCollection.remove(post.getId());
            postCollection.put(post.getId(), post);
        } else {
            throw new NotFoundException("Выбран несуществующий или удалённый пост");
        }
        return post;
    }

    public void removeById(long id) {
        removedPostCollection.put(id,postCollection.get(id));
        postCollection.remove(id);
    }
}