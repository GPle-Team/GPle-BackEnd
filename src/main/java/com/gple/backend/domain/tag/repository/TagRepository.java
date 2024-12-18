package com.gple.backend.domain.tag.repository;

import com.gple.backend.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllTagByPostId(Long postId);
}
