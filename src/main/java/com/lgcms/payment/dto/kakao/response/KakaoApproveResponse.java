package com.lgcms.payment.dto.kakao.response;

import lombok.Builder;
import lombok.Data;

@Data
public class KakaoApproveResponse {

    private String tid;
    private String cid;
    private KakaoApproveAmount amount;

}
