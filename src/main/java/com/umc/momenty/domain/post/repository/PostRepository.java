package com.umc.momenty.domain.post.repository;

import com.umc.momenty.domain.post.entity.Post;
import com.umc.momenty.domain.post.enums.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository <Post, Long> {
    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE (:category IS NULL OR p.category = :category) " +
            "AND (:keyword IS NULL OR p.title LIKE %:keyword%)")
    Page<Post> findAllByCategoryAndKeyword(@Param("category") PostCategory category,
                                           @Param("keyword") String keyword,
                                           Pageable pageable);

}
