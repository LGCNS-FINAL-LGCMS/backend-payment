package com.lgcms.payment.dto.kakao.response;

import lombok.Data;

@Data
public class KakaoApproveAmount {

    private Integer total;
    private Integer tax_free;
}
