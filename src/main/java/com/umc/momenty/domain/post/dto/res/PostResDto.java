package com.umc.momenty.domain.post.dto.res;

import com.umc.momenty.domain.post.enums.PostCategory;
import com.umc.momenty.global.dto.res.PageResDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class PostResDto {

    @Builder
    public record PostListDTO(
            Long postId,
            PostCategory category,
            String title,
            String contentPreview,
            String authorName,
            LocalDateTime createdAt,
            int likeNum,
            int commentNum,
            boolean isLiked
    ) {}

    @Builder
    public record PostPageDTO(
            List<PostListDTO> posts,
            PageResDTO.PageInfoDTO pageInfo
    ) {}

}
