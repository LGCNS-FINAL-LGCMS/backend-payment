package com.lgcms.payment.controller;

import com.lgcms.payment.common.dto.BaseResponse;
import com.lgcms.payment.dto.request.CartAddRequest;
import com.lgcms.payment.dto.response.CartListResponse;
import com.lgcms.payment.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Slf4j
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("")
    public ResponseEntity<BaseResponse> addCart(@RequestBody CartAddRequest cartAddRequest,
                                                @RequestHeader("X-USER-ID") String id){
        Long memberId = Long.parseLong("1");
        cartService.addCart(memberId, cartAddRequest);

        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse> getCartList(@RequestHeader("X-USER-ID") String id){

        Long memberId = Long.parseLong("1");

        CartListResponse cartListResponse = cartService.getCartList(memberId);

        return ResponseEntity.ok(BaseResponse.ok(cartListResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteCart(@PathVariable("id") Long cartId){

        cartService.deleteCart(cartId);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }
}
