package com.lgcms.payment.controller;


import com.lgcms.payment.common.dto.BaseResponse;
import com.lgcms.payment.dto.request.PaymentApproveRequest;
import com.lgcms.payment.dto.request.PaymentReadyRequest;
import com.lgcms.payment.dto.response.PaymentReadyResponse;
import com.lgcms.payment.service.CartService;
import com.lgcms.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final CartService cartService;

    @PostMapping("/ready")
    public ResponseEntity<BaseResponse> readyKakaoPayment(@RequestBody PaymentReadyRequest paymentReadyRequest,
                                                          @RequestHeader("X-USER-ID") Long memberId){
        PaymentReadyResponse paymentResponse = paymentService.getReady(paymentReadyRequest, memberId);
        return ResponseEntity.ok(BaseResponse.ok(paymentResponse));
    }

    @PostMapping("/list/ready")
    public ResponseEntity<BaseResponse> listReadyKakaoPayment(@RequestBody List<PaymentReadyRequest> paymentReadyRequests,
                                                          @RequestHeader("X-USER-ID") Long memberId){
        PaymentReadyResponse paymentResponse = paymentService.getListReady(paymentReadyRequests, memberId);
        return ResponseEntity.ok(BaseResponse.ok(paymentResponse));
    }

    @PostMapping("/approve")
    public ResponseEntity<BaseResponse> approveKakaoPayment(@RequestBody PaymentApproveRequest paymentApproveRequest,
                                                            @RequestHeader("X-USER-ID") Long memberId){
        paymentService.getApprove(paymentApproveRequest, memberId);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }
}
