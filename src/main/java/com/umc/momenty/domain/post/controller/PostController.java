package com.umc.momenty.domain.post.controller;

import com.umc.momenty.domain.post.dto.req.CommentReqDTO;
import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import com.umc.momenty.domain.post.dto.res.PostResDto;
import com.umc.momenty.domain.post.enums.PostCategory;
import com.umc.momenty.domain.post.exception.code.PostSuccessCode;
import com.umc.momenty.domain.post.service.PostService;
import com.umc.momenty.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController implements PostControllerDocs{

    private final PostService postService;

    @PostMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Long> createPost(
            @PathVariable Long userId,
            @RequestPart("request") PostReqDTO.CreatePostDTO createPost,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        Long postId = postService.createPost(userId, createPost, images);
        return ApiResponse.onSuccess(PostSuccessCode.POST_CREATED, postId);
    }

    @GetMapping("/{userId}")
    public ApiResponse<PostResDto.PostPageDTO> getPosts(
            @PathVariable Long userId,
            @RequestParam(required = false) PostCategory category,
            @RequestParam(required = false) String keyword,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return ApiResponse.onSuccess(PostSuccessCode.POST_LIST_FOUND, postService.getAllPosts(userId, category, keyword, pageable));
    }

    @PostMapping("/{postId}/comments/{userId}")
    public ApiResponse<Long> createComment(
            @PathVariable Long postId,
            @PathVariable Long userId,
            @RequestBody CommentReqDTO.CreateCommentDTO createComment
    ) {
        Long commentId = postService.createComment(userId, postId, createComment);
        return ApiResponse.onSuccess(PostSuccessCode.COMMENT_CREATED, commentId);
    }

    @GetMapping("/{postId}/detail/{userId}")
    public ApiResponse<PostResDto.PostDetailDTO> getPostDetail(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        return ApiResponse.onSuccess(PostSuccessCode.POST_DETAIL_FOUND, postService.getPostDetail(userId, postId));
    }
}
