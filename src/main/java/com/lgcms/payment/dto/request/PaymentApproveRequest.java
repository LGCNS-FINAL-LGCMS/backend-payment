package com.lgcms.payment.dto.request;

import java.util.List;

public record PaymentApproveRequest(String tid, String token, List<Long> cartId, List<String> lectureId) {
}
