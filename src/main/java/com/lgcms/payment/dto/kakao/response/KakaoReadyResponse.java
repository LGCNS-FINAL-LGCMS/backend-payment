package com.lgcms.payment.dto.kakao.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KakaoReadyResponse {

    private String tid;
    private String next_redirect_pc_url;
    private LocalDateTime created_at;
}
