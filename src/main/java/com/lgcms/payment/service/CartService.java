package com.lgcms.payment.service;

import com.lgcms.payment.domain.Cart;
import com.lgcms.payment.dto.request.CartAddRequest;
import com.lgcms.payment.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    @Transactional
    public void addCart(Long memberId, CartAddRequest cartAddRequest) {
        Cart cart = Cart.builder()
                .memberId(memberId)
                .title(cartAddRequest.title())
                .lectureId(cartAddRequest.lectureId())
                .price(cartAddRequest.price())
                .build();

        cartRepository.save(cart);
    }


}
