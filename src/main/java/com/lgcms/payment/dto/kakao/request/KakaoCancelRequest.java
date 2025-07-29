package com.lgcms.payment.dto.kakao.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoCancelRequest {

    private String cid;

    private String tid;

    private Integer cancel_amount;

    private Integer cancel_tax_free_amount;
}
