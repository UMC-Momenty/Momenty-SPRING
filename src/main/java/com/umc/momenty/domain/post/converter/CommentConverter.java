package com.umc.momenty.domain.post.converter;

import com.umc.momenty.domain.post.dto.req.CommentReqDTO;
import com.umc.momenty.domain.post.entity.Comment;
import com.umc.momenty.domain.post.entity.Post;
import com.umc.momenty.domain.user.entity.User;

public class CommentConverter {

    public static Comment toComment(User user, Post post, CommentReqDTO.CreateCommentDTO dto) {
        return Comment.builder()
                .content(dto.content())
                .parentId(dto.parentCommentId())
                .isAnonymous(dto.isAnonymous())
                .user(user)
                .post(post)
                .build();
    }
}
