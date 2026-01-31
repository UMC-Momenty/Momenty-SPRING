package com.umc.momenty.domain.post.converter;

import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import com.umc.momenty.domain.post.entity.Post;
import com.umc.momenty.domain.post.entity.PostImage;
import com.umc.momenty.domain.user.entity.User;

import java.util.List;

public class PostConverter {

    public static Post toPost(User user, PostReqDTO.CreatePostDTO createPostDTO) {
        Post post = Post.builder()
                .category(createPostDTO.category())
                .title(createPostDTO.title())
                .content(createPostDTO.content())
                .user(user)
                .build();

        if (createPostDTO.imageUrls() != null) {
            createPostDTO.imageUrls().forEach(imageUrl -> {
                PostImage postImage = PostImage.builder()
                        .imageUrl(imageUrl)
                        .build();

                post.addPostImage(postImage);
            });
        }
        return post;
    }


}
