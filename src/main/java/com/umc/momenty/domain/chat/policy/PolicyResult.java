package com.umc.momenty.domain.chat.policy;

public record PolicyResult(boolean allowed, String rejectMessage) {

    public static PolicyResult allow() {
        return new PolicyResult(true, null);
    }

    public static PolicyResult reject(String msg) {
        return new PolicyResult(false, msg);
    }
}
