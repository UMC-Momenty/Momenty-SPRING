package com.umc.momenty.domain.post.repository;

import com.umc.momenty.domain.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 추가: soft delete된 댓글 제외하고 조회
    @Query("SELECT c FROM Comment c " +
            "WHERE c.post.id = :postId AND c.deletedAt IS NULL")
    List<Comment> findByPostIdAndDeletedAtIsNull(@Param("postId") Long postId);
}
