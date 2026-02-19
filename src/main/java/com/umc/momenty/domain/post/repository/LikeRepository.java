package com.umc.momenty.domain.post.repository;

import com.umc.momenty.domain.post.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository <Like, Long> {
    @Query("SELECT l.post.id FROM Like l WHERE l.user.id = :userId AND l.post.id IN :postIds")
    List<Long> findLikedPostIdsByUserIdAndPostIds(@Param("userId") Long userId,
                                                  @Param("postIds") List<Long> postIds);

}
