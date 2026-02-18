package com.umc.momenty.domain.post.repository;

import com.umc.momenty.domain.post.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository <PostImage, Long> {
}
