package com.cloud.sample.service.memberservice.domain;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStateCode{
    WAIT("00", "대기"),
    NORMAL("01", "정상"),
    HALT("07", "정지"),
    LEAVE("08", "탈퇴"),
    DELETE("09", "삭제");

    private final String key;
    private final String title;

    // 사용자 상태코드 상수 검색
    public static MemberStateCode findByKey(String key){
        return Arrays.stream(MemberStateCode.values()).filter(c -> c.getKey().equals(key)).findAny().orElse(null);
    }
}