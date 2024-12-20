package com.gple.backend.domain.post.repository;

import com.gple.backend.domain.post.entity.Location;
import com.gple.backend.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);

    List<Post> findByLocation(Location location);
}
