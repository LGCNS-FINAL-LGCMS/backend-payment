package com.lgcms.payment.dto.response;

public record CartResponse(Long cartId,String lectureId, String title, Long price, String thumbnailUrl) {
}
