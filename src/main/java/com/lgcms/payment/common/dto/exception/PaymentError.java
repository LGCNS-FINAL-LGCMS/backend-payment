package com.lgcms.payment.common.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PaymentError implements ErrorCodeInterface {
    PAYMENT_NOT_FOUND("PMTE-01", "결제 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

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
