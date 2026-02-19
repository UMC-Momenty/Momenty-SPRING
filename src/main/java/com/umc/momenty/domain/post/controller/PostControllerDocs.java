package com.umc.momenty.domain.post.controller;

import com.umc.momenty.domain.post.dto.req.CommentReqDTO;
import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import com.umc.momenty.domain.post.dto.res.PostResDto;
import com.umc.momenty.domain.post.enums.PostCategory;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostControllerDocs {
    @Operation(summary = "게시글 작성 API", description = "해당 사용자가 게시글을 작성합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    ApiResponse<Long> createPost(
            @Parameter(description = "작성할 사용자 ID") Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            encoding = @Encoding(name = "request", contentType = "application/json")
                    )
            )
            PostReqDTO.CreatePostDTO createPost,
            @Parameter(description = "업로드할 이미지 파일 리스트", content = @Content(mediaType = "multipart/form-data"))
            List<MultipartFile> images
    );

    @Operation(summary = "게시글 리스트 조회 API", description = "해당 사용자의 게시글을 페이지네이션으로 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 페이지 요청")
    })
    ApiResponse<PostResDto.PostPageDTO> getPosts(
            @Parameter(description = "조회할 사용자 ID") Long userId,
            @Parameter(description = "선택적, 게시글 카테고리 필터") PostCategory category,
            @Parameter(description = "선택적, 제목 키워드 검색") String keyword,
            @ParameterObject @Parameter(description = "페이지네이션 정보") Pageable pageable
    );

    @Operation(summary = "댓글 작성 API", description = "게시글에 댓글을 작성합니다.")
    ApiResponse<Long> createComment(
            @Parameter(description = "게시글 ID") Long postId,
            @Parameter(description = "작성할 사용자 ID") Long userId,
            @RequestBody CommentReqDTO.CreateCommentDTO createComment
    );

    @Operation(summary = "게시글 상세 조회 API", description = "게시글 상세 내용과 댓글을 조회합니다.")
    ApiResponse<PostResDto.PostDetailDTO> getPostDetail(
            @Parameter(description = "게시글 ID") Long postId,
            @Parameter(description = "조회할 사용자 ID") Long userId
    );
}
