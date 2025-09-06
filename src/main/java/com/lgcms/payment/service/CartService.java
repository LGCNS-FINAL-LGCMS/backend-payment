package com.lgcms.payment.service;

import com.lgcms.payment.domain.Cart;
import com.lgcms.payment.dto.request.CartAddRequest;
import com.lgcms.payment.dto.response.CartListResponse;
import com.lgcms.payment.dto.response.CartResponse;
import com.lgcms.payment.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .thumbnailUrl(cartAddRequest.thumbnailUrl())
                .lectureId(cartAddRequest.lectureId())
                .price(cartAddRequest.price())
                .build();

        cartRepository.save(cart);
    }

    @Transactional
    public CartListResponse getCartList(Long memberId) {
        List<Cart> carts = cartRepository.findAllByMemberId(memberId);
        System.out.println(memberId);
       List<CartResponse> cartList = carts.stream()
                .map(cart -> new CartResponse(cart.getId(),
                        cart.getLectureId(),
                        cart.getTitle(),
                        cart.getPrice(),
                        cart.getThumbnailUrl()))
                .toList();

        return new CartListResponse(cartList);
    }

    @Transactional
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Transactional
    public Long getMemberCartCount(Long memberId) {

        Long count = cartRepository.countByMemberId(memberId);

        return count;
    }

    @Transactional
    public void deleteCartItems(List<Long> ids) {
        for(Long id : ids){
            cartRepository.deleteById(id);
        }
    }
}
