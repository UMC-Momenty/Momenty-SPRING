package com.umc.momenty.domain.post.service;

import com.umc.momenty.domain.post.dto.req.CommentReqDTO;
import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import com.umc.momenty.domain.post.dto.res.PostResDto;
import com.umc.momenty.domain.post.enums.PostCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface PostService {
    Long createPost(Long userId, PostReqDTO.CreatePostDTO createPost, List<MultipartFile> images);
    PostResDto.PostPageDTO getAllPosts(Long userId, PostCategory category, String keyword, Pageable pageable);
    Long createComment(Long userId, Long postId, CommentReqDTO.CreateCommentDTO createComment);
    PostResDto.PostDetailDTO getPostDetail(Long userId, Long postId);
}
