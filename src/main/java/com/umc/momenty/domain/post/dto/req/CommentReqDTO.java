package com.umc.momenty.domain.post.dto.req;

public class CommentReqDTO {

    public record CreateCommentDTO(
            String content,
            Long parentCommentId,
            boolean isAnonymous
    ){}
}
