package com.lgcms.payment.common.config.payment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kakao")
@Getter@Setter
public class KakaoConfig {
    private String cid;
    private String partnerOrderId;
    private String partnerUserId;
    private String approvalUrl;
    private String cancelUrl;
    private String failUrl;
    private Integer taxFreeAmount;
    private String secret;
}
