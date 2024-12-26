package com.gple.backend.domain.post.repository;

import com.gple.backend.domain.post.entity.Location;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);

    List<Post> findByLocation(Location location);
}
