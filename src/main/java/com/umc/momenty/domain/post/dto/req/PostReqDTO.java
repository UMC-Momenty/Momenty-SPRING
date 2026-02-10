package com.umc.momenty.domain.post.dto.req;

import com.umc.momenty.domain.post.enums.PostCategory;

public class PostReqDTO {

    public record CreatePostDTO(
            PostCategory category,
            String title,
            String content,
            boolean isAnonymous
    ){}
}
