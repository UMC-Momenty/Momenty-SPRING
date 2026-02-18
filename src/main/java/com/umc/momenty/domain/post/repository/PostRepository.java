package com.umc.momenty.domain.post.repository;

import com.umc.momenty.domain.post.entity.Post;
import com.umc.momenty.domain.post.enums.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 수정: deletedAt IS NULL 조건 추가 (단건 조회)
    @Query("SELECT p FROM Post p " +
            "WHERE p.id = :postId AND p.deletedAt IS NULL")
    Optional<Post> findByIdAndDeletedAtIsNull(@Param("postId") Long postId);

    // 수정: 목록 조회에 deletedAt IS NULL 조건 추가
    @Query("SELECT p FROM Post p " +
            "WHERE p.deletedAt IS NULL " +
            "AND (:category IS NULL OR p.category = :category) " +
            "AND (:keyword IS NULL OR p.title LIKE %:keyword%)")
    Page<Post> findAllByCategoryAndKeyword(@Param("category") PostCategory category,
                                           @Param("keyword") String keyword,
                                           Pageable pageable);
}
