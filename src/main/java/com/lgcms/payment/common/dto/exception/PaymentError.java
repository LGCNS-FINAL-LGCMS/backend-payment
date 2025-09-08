package com.lgcms.payment.common.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PaymentError implements ErrorCodeInterface {
    PAYMENT_NOT_FOUND("PMTE-01", "결제 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ALREADY_PAYMENT_LECTURE("PMTE-02","이미 결제된 상품입니다.", HttpStatus.BAD_REQUEST),
    PAYMENT_FAIL("PMTE-03", "내부서버 오류로 결제에 실패하여 취소처리 되었습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATED_CART_ITEM("PMTE-04","장바구니에 추가된 강의입니다.", HttpStatus.BAD_REQUEST);

    private final String status;
    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.builder()
                .status(status)
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }
}
