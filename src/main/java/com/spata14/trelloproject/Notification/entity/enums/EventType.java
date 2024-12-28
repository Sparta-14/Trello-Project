package com.spata14.trelloproject.Notification.entity.enums;


public enum EventType {
    WORKSPACE("workspace"),
    CARD("card"),
    COMMENT("comment");

    private String type;

    /**
     * 지정된 문자열 식별자를 사용하여 {@link EventType}을 생성
     *
     * @param type 이벤트 유형의 문자열 식별자
     */
    EventType(String type) {
        this.type = type;
    }

    /**
     * 이벤트 유형의 문자열 식별자 반환
     *
     * @return 이벤트 유형의 문자열 식별자
     */
    public String getType() {
        return type;
    }
}