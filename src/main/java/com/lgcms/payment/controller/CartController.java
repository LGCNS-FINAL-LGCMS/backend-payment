package com.lgcms.payment.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.lgcms.payment.common.dto.BaseResponse;
import com.lgcms.payment.dto.request.CartAddRequest;
import com.lgcms.payment.dto.response.CartListResponse;
import com.lgcms.payment.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/student/payment")
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<BaseResponse> addCart(@RequestBody CartAddRequest cartAddRequest,
                                                @RequestHeader("X-USER-ID") Long memberId){
        cartService.addCart(memberId, cartAddRequest);

        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @GetMapping("/cart")
    public ResponseEntity<BaseResponse> getCartList(@RequestHeader("X-USER-ID") Long memberId){

        CartListResponse cartListResponse = cartService.getCartList(memberId);

        return ResponseEntity.ok(BaseResponse.ok(cartListResponse));
    }

    @GetMapping("/cart/count")
    public ResponseEntity<BaseResponse> getMemberCartCount(@RequestHeader("X-USER-ID") Long memberId){

        Long count = cartService.getMemberCartCount(memberId);

        return ResponseEntity.ok(BaseResponse.ok(count));
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<BaseResponse> deleteCart(@PathVariable("id") Long cartId){

        cartService.deleteCart(cartId);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }
}
