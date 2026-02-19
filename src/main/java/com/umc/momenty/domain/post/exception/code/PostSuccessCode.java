package com.umc.momenty.domain.post.exception.code;

import com.umc.momenty.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostSuccessCode implements BaseSuccessCode {
    POST_CREATED(HttpStatus.OK, "POST200-1", "성공적으로 게시글이 생성되었습니다."),
    POST_LIST_FOUND(HttpStatus.OK,"POST200-2", "성공적으로 게시글 목록을 조회했습니다."),
    COMMENT_CREATED(HttpStatus.OK, "POST200-3", "성공적으로 댓글이 생성되었습니다."),
    POST_DETAIL_FOUND(HttpStatus.OK, "POST200-4", "성공적으로 게시글 상세를 조회했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
