package com.practice.oauth.userinfo;

import java.util.Map;

/**
 * 네이버 로그인 API에서 받아오는 유저 정보를 담는 클래스
 */
public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }
        return (String) response.get("id");
    }

    @Override
    public String getNickname() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("nickname");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("profile_image");
    }
}

/*
 * 네이버 유저정보 response json 예시
 * {
 *   "resultcode": "00",
 *   "message": "success",
 *   "response": {
 *     "email": "openapi@naver.com",
 *     "nickname": "OpenAPI",
 *     "profile_image": "https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif",
 *     "age": "40-49",
 *     "gender": "F",
 *     "id": "32742776",
 *     "name": "오픈 API",
 *     "birthday": "10-01"
 *   }
 * }
 * https://developers.naver.com/docs/login/web/web.md
 */