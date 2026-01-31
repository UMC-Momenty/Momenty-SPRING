package com.umc.momenty.domain.post.dto.req;

import com.umc.momenty.domain.post.enums.PostCategory;

import java.util.List;

public class PostReqDTO {

    public record CreatePostDTO(
            PostCategory category,
            String title,
            String content,
            List <String> imageUrls
    ){}
}
