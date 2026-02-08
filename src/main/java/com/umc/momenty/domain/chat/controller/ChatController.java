package com.umc.momenty.domain.chat.controller;

import com.umc.momenty.domain.chat.dto.req.ChatReqDTO;
import com.umc.momenty.domain.chat.dto.res.ChatResDTO;
import com.umc.momenty.domain.chat.service.query.ChatQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController implements ChatControllerDocs {

    private final ChatQueryService chatQueryService;

    @PostMapping("/{userId}")
    @Override
    public ChatResDTO.ChatResponse chat(@PathVariable Long userId, @RequestBody ChatReqDTO.ChatRequest request){
        return chatQueryService.chat(userId, request.message());
    }
}
