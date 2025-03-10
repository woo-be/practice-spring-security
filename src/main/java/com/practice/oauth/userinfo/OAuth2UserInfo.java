package com.practice.oauth.userinfo;

import java.util.Map;

/**
 * 소셜 타입별로 유저 정보를 가지는 추상클래스.
 * 이 클래스를 상속받아 각 소셜 타입의 유저 정보 클래스를 구현
 */
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId(); //소셜 식별 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"

    public abstract String getNickname();

    public abstract String getImageUrl();

}
