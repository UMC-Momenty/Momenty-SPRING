package com.umc.momenty.domain.post.controller;

import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import com.umc.momenty.domain.post.dto.res.PostResDto;
import com.umc.momenty.domain.post.enums.PostCategory;
import com.umc.momenty.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;

public interface PostControllerDocs {
    @Operation(summary = "게시글 작성 API", description = "해당 사용자가 게시글을 작성합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    ApiResponse<Long> createPost(
            @Parameter(description = "작성할 사용자 ID") Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = """
                            게시글 작성 요청 DTO
                            
                            **요청 예시:** 
                            - title: "게시글 제목"
                            - content: "게시글 내용"
                            - category: "QUESTION"
                            """,
                    required = true
            )
            PostReqDTO.CreatePostDTO createPost
    );

    @Operation(summary = "게시글 리스트 조회 API",
            description = """
                    해당 사용자의 게시글을 페이지네이션으로 조회합니다.
                    
                    - page : 페이지 번호 (0부터 시작)
                    - size : 페이지 크기
                    - sort : 정렬 기준 (예: createdAt,desc)
                    - category : 선택적, 카테고리 필터
                    - keyword : 선택적, 제목 키워드 검색
                    """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 페이지 요청")
    })
    ApiResponse<PostResDto.PostPageDTO> getPosts(
            @Parameter(description = "조회할 사용자 ID") Long userId,
            @Parameter(description = "선택적, 게시글 카테고리 필터") PostCategory category,
            @Parameter(description = "선택적, 제목 키워드 검색") String keyword,
            @ParameterObject @Parameter(description = "페이지네이션 정보 (page, size, sort)") Pageable pageable
    );

}